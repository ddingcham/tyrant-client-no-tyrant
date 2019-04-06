package pe.msbaek.mock;


public class MockTyrant {

    public static synchronized void run() {

    }

    static class Coordinator extends Thread {
        // Coordinating Daemons(Request, Response, Task)
        // Observing Daemon's Condition
    }

    static class RequestDaemon extends Thread {
        // Observing - Tyrant's TyrantInput (ClientInfo)
        // Publishing Task to Task Queue
    }

    static class ResponseDaemon extends Thread {
        // Response - Client's TyrantInputStream(TyrantOutput - ClientInfo)
        // Depend on TaskRunnerDaemon's work
    }

    static class TaskRunnerDaemon extends Thread {
        // Subscribe Task from TaskQueue
        // Notifying Result to ResponseDaemon
    }

}
