package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cube;
import fertdt.repositories.ICubeRepository;

import java.util.List;

public class CubeService implements ICubeService {
    private final ICubeRepository cubeRepository;

    public CubeService(ICubeRepository cubeRepository) {
        this.cubeRepository = cubeRepository;
    }

    @Override
    public List<Cube> getAllCubes() throws DatabaseException {
        return cubeRepository.getAllCubes();
    }

    @Override
    public Cube getCubeByName(String name) throws DatabaseException {
        return cubeRepository.getCubeByName(name);
    }
}
