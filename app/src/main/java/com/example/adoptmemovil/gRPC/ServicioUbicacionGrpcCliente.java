package com.example.adoptmemovil.gRPC;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import ubicacionGrpc.ServicioUbicacionGrpc;
import ubicacionGrpc.SolicitudCercana;
import ubicacionGrpc.SolicitudesCercanas;
import ubicacionGrpc.Ubicacion;

public class ServicioUbicacionGrpcCliente {

    private final ManagedChannel channel;
    private final ServicioUbicacionGrpc.ServicioUbicacionFutureStub stub;
    private final ExecutorService executor;

    public ServicioUbicacionGrpcCliente() {
        channel = ManagedChannelBuilder
                .forAddress("192.168.100.139", 50051)
                .usePlaintext()
                .build();

        stub = ServicioUbicacionGrpc.newFutureStub(channel);
        executor = Executors.newSingleThreadExecutor();
    }

    public void obtenerSolicitudesCercanas(int usuarioId, double latitud, double longitud) {
        Ubicacion request = Ubicacion.newBuilder()
                .setUsuarioId(usuarioId)
                .setLatitud(latitud)
                .setLongitud(longitud)
                .build();

        com.google.common.util.concurrent.ListenableFuture<SolicitudesCercanas> future =
                stub.obtenerSolicitudesCercanas(request);

        future.addListener(() -> {
            try {
                SolicitudesCercanas response = future.get();
                for (SolicitudCercana solicitud : response.getResultadosList()) {
                    System.out.println("ID solicitud: " + solicitud.getSolicitudAdopcionId());
                    System.out.println("Distancia: " + solicitud.getDistancia());
                    System.out.println("Lat: " + solicitud.getLatitud() + ", Lon: " + solicitud.getLongitud());
                }
            } catch (ExecutionException ex) {
                Throwable causa = ex.getCause();
                if (causa instanceof StatusRuntimeException) {
                    StatusRuntimeException statusEx = (StatusRuntimeException) causa;
                    System.err.println("Error de gRPC: " + statusEx.getStatus());
                } else {
                    System.err.println("Error inesperado: " + causa);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.err.println("La operación fue interrumpida");
            } catch (Exception ex) {
                System.err.println("Error general: " + ex);
            }
        }, executor);
    }

    public ServicioUbicacionGrpc.ServicioUbicacionFutureStub getStub() {
        return stub;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
    }

}