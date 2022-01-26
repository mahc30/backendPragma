package backendJava.client.service;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.exception.EmptyListException;
import backendJava.client.repository.ClienteRepository;
import backendJava.client.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{
    private final ClienteRepository clienteRepository;
    private final FotoRepository fotoRepository;

    @Override
    public List<Cliente> listAllCliente() {

        List<Cliente> clients = clienteRepository.findAll();
        if(clients.isEmpty()) throw new EmptyListException();
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getCliente(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {

        Cliente clienteDB = getCliente(cliente.getId());
        if(clienteDB == null) return null;

        clienteDB.setNombres(cliente.getNombres());
        clienteDB.setApellidos(cliente.getApellidos());
        clienteDB.setEdad(cliente.getEdad());
        clienteDB.setCiudad(cliente.getCiudad());
        clienteDB.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteDB.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        clienteDB.setFotoMongoId(cliente.getFotoMongoId());

        return clienteRepository.save(clienteDB);
    }

    @Override
    public void deleteCliente(Long id) {
        Cliente clienteDB = getCliente(id);
        fotoRepository.deleteById(clienteDB.getFotoMongoId());

        if(clienteDB != null) clienteRepository.deleteById(id);
    }

    @Override
    public Cliente findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoId, String numeroIdentificacion) {
        Cliente cliente = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroIdentificacion);
        if(cliente == null) return null;

        return cliente;
    }

    @Override
    public List<Cliente> findByEdadGreaterThan(int edad) {
        List<Cliente> clientes = clienteRepository.findByEdadGreaterThan(edad);
        if(clientes.isEmpty()) throw new EmptyListException();
        return clientes;
    }


}
