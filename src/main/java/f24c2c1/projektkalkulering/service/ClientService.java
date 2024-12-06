package f24c2c1.projektkalkulering.service;

import f24c2c1.projektkalkulering.model.Client;
import f24c2c1.projektkalkulering.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Fetches all clients.
     *
     * @return List of all clients.
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * Fetches a client by ID.
     *
     * @param id The ID of the client.
     * @return The client, or null if not found.
     */
    public Client getClientById(long id) {
        return clientRepository.findById(id);
    }

    /**
     * Creates a new client.
     *
     * @param client The client to create.
     */
    public void createClient(Client client) {
        clientRepository.save(client);
    }

    /**
     * Updates an existing client.
     *
     * @param client The client to update.
     */
    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    /**
     * Deletes a client by ID.
     *
     * @param id The ID of the client to delete.
     */
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }
}
