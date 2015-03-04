package server.sceleton;

import entity.Queue;
import server.persistence.Client;
import server.persistence.ClientManager;

public class ClientSocketRemover extends Thread{

    private static Queue<ClientSocket> queue = new Queue<ClientSocket>();

    public ClientSocketRemover() {
        super("ClientSocketRemover");
    }

    public String getThread() {
        return super.getName() + isAlive();
    }

    public static void addClientSocketToRemove(ClientSocket clientSocket) {
        queue.add(clientSocket);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            ClientSocket clientSocket = queue.remove();

            Client client = clientSocket.getClient();
            if (client != null) {
                ClientManager.removeClient(client);

            }
            ClientSocketManager.close(clientSocket);

        }
    }

}
