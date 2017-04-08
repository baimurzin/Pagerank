package service;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by vladislav on 08.04.17.
 */
public class PageRankCalc {

    private static final double DAMPING_FACTOR = 0.85;
    private static final double PRECISION = 1.0e-4;
    private long time;

    private List<Double> ranks;
    private List<Double> prevRanks;

    public List<Double> calculate(IStorageManager storage) {
        long start = System.currentTimeMillis();
        init(storage);
        do {
            prevRanks = new ArrayList<>(ranks);
            IntStream.range(0, storage.getSize())
                    .forEach(i -> {
                        final double[] newRank = {(1 - DAMPING_FACTOR) / storage.getSize()};
                        storage.getTo(i).forEach(in -> {
                            int out = storage.getFrom(in).size();
                            newRank[0] += DAMPING_FACTOR * prevRanks.get(in) * 1D / (out == 0 ? 1 : out);
                        });
                        ranks.set(i, newRank[0]);
                    });
            normalize();
        } while (diff(ranks, prevRanks) > PRECISION);
        long end = System.currentTimeMillis();
        this.time = end - start;
        return ranks;
    }

    private void init(IStorageManager storage) {
        ranks = new ArrayList<>(storage.getSize());
        IntStream.range(0, storage.getSize())
                .forEach(r -> {
                    ranks.add(1D / storage.getSize());
                });
    }

    protected void normalize() {
        RealVector vector = MatrixUtils.createRealVector(toArray(ranks));
        double distance = vector.getNorm();
        List<Double> newRanks = new ArrayList<>();
        ranks.forEach(r -> newRanks.add(r / distance));
        this.ranks = newRanks;
    }

    protected double diff(List<Double> ranks, List<Double> prevRanks) {
        RealVector m1 = MatrixUtils.createRealVector(toArray(ranks));
        RealVector m2 = MatrixUtils.createRealVector(toArray(prevRanks));
        return m1.subtract(m2).getNorm();
    }

    protected double[] toArray(List<Double> ranks) {
        double[] vector = new double[ranks.size()];
        IntStream.range(0, ranks.size()).forEach(r -> vector[r] = ranks.get(r));
        return vector;
    }

    public long getTime() {
        return time;
    }
}
