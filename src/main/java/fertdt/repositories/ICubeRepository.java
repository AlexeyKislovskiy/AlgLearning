package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cube;

import java.util.List;

public interface ICubeRepository {
    List<Cube> getAllCubes() throws DatabaseException;

    Cube getCubeByName(String name) throws DatabaseException;
}
