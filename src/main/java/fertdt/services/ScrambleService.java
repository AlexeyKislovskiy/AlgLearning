package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.repositories.IScrambleRepository;

public class ScrambleService implements IScrambleService {
    private final IScrambleRepository scrambleRepository;

    public ScrambleService(IScrambleRepository scrambleRepository) {
        this.scrambleRepository = scrambleRepository;
    }

    @Override
    public String getScramble(int situationId) throws DatabaseException {
        return scrambleRepository.getScramble(situationId);
    }
}
