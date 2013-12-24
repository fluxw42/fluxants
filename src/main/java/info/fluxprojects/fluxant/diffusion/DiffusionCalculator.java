package info.fluxprojects.fluxant.diffusion;

import info.fluxprojects.fluxant.core.Direction;
import info.fluxprojects.fluxant.core.Position;
import info.fluxprojects.fluxant.utils.MapUtils;

public class DiffusionCalculator {

    public static final double DIFFUSION_FACTOR = 4.0;
    private final int cols;
    private final int rows;
    private final boolean inverseDiffusionFactor;
    private final long[][] diffusionMap;
    private final boolean[][] diffusionFactor;
    private final boolean[][] ants;
    private final boolean[][] waterMap;

    public DiffusionCalculator(long[][] diffusionMap, boolean[][] waterMap, boolean[][] diffusionFactor, boolean[][] ants, boolean inverseDiffusionFactor) {
        this.cols = MapUtils.getCols(diffusionFactor);
        this.rows = MapUtils.getRows(diffusionFactor);
        this.diffusionMap = diffusionMap;
        this.waterMap = waterMap;
        this.diffusionFactor = diffusionFactor;
        this.ants = ants == null ? new boolean[cols][rows] : ants;
        this.inverseDiffusionFactor = inverseDiffusionFactor;
    }

    public DiffusionCalculator(long[][] diffusionMap, boolean[][] waterMap, boolean[][] diffusionFactor, boolean[][] ants) {
        this(diffusionMap, waterMap, diffusionFactor, ants, false);
    }

    public DiffusionCalculator(long[][] diffusionMap, boolean[][] waterMap, boolean[][] diffusionFactor) {
        this(diffusionMap, waterMap, diffusionFactor, null, false);
    }

    public DiffusionCalculator(long[][] diffusionMap, boolean[][] waterMap, boolean[][] diffusionFactor, boolean inverseDiffusionFactor) {
        this(diffusionMap, waterMap, diffusionFactor, null, inverseDiffusionFactor);
    }

    public void calcDiffusionMap(int rounds) {
        for (int i = 0; i < rounds; i++) {
            initDiffusionMap(diffusionMap);
            calcDiffusionMap(diffusionMap);
            initDiffusionMap(diffusionMap);
        }
    }

    private void calcDiffusionMap(long[][] diffusionMap) {
        long[][] originalDiffusionMap = copyArray(diffusionMap);
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                Position cp = new Position(c, r);
                Position np = MapUtils.getPosition(cp, Direction.NORTH, cols, rows);
                Position ep = MapUtils.getPosition(cp, Direction.EAST, cols, rows);
                Position sp = MapUtils.getPosition(cp, Direction.SOUTH, cols, rows);
                Position wp = MapUtils.getPosition(cp, Direction.WEST, cols, rows);

                long cv = originalDiffusionMap[c][r];
                long nv = originalDiffusionMap[np.getCol()][np.getRow()];
                long ev = originalDiffusionMap[ep.getCol()][ep.getRow()];
                long sv = originalDiffusionMap[sp.getCol()][sp.getRow()];
                long wv = originalDiffusionMap[wp.getCol()][wp.getRow()];

                double nd = (nv - cv) / DIFFUSION_FACTOR;
                double ed = (ev - cv) / DIFFUSION_FACTOR;
                double sd = (sv - cv) / DIFFUSION_FACTOR;
                double wd = (wv - cv) / DIFFUSION_FACTOR;

                double d = nd + ed + sd + wd;

                diffusionMap[c][r] = Math.round(cv + d);

            }
        }
    }

    private long[][] copyArray(long[][] src) {
        long[][] dst = new long[cols][rows];
        for (int c = 0; c < diffusionMap.length; c++) {
            System.arraycopy(diffusionMap[c], 0, dst[c], 0, diffusionMap[c].length);
        }
        return dst;
    }

    private long[][] initDiffusionMap(long[][] map) {
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                if (waterMap[c][r]) {
                    map[c][r] = 0;
                } else if (diffusionFactor[c][r] ^ inverseDiffusionFactor) {
                    map[c][r] = Long.MAX_VALUE;
                } else if (ants[c][r]) {
                    map[c][r] /= 4;
                }
            }
        }
        return map;
    }

}
