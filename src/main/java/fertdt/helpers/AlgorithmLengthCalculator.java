package fertdt.helpers;

public class AlgorithmLengthCalculator implements IAlgorithmLengthCalculator {
    @Override
    public int length(boolean interceptionFlag, boolean doubleMoveFlag, String text) {
        String[] moves = text.split(" ");
        int ans = 0;
        for (String move : moves) {
            ans += moveLength(interceptionFlag, doubleMoveFlag, move);
        }
        return ans;
    }

    private int moveLength(boolean interceptionFlag, boolean doubleMoveFlag, String move) {
        if (move.charAt(0) == 'x' || move.charAt(0) == 'y' || move.charAt(0) == 'z') {
            if (!interceptionFlag) return 0;
        }
        if (doubleMoveFlag && move.charAt(move.length() - 1) == '2') return 2;
        else return 1;
    }
}
