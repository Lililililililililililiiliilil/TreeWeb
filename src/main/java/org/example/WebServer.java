package org.example;


import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

/**
 * Консольное приложение, запускающее web-сервер.
 */
public class WebServer {

    public static void main(String[] argv) {
        start();
    }

    /**
     * Запускает web-сервер. По окончании работы требуется ручная остановка процесса.
     */
    private static void start() {
        UndertowJaxrsServer server = new UndertowJaxrsServer().start();
        server.deploy(RestApplication.class);
        System.out.println("Сервер запущен: http://localhost:8081/");

    }
}
