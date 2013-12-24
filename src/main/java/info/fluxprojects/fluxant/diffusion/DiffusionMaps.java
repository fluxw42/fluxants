package info.fluxprojects.fluxant.diffusion;

public class DiffusionMaps {

    private final long[][] scoutDiffusionMap;
    private final long[][] coverageDiffusionMap;
    private final long[][] foodDiffusionMap;
    private final long[][] enemyDiffusionMap;
    private final long[][] enemyHillDiffusionMap;
    private final long[][] mixedDiffusionMap;

    private final int cols;
    private final int rows;
    private final DiffusionBot diffusionBot;

    public DiffusionMaps(DiffusionBot diffusionBot) {
        this.diffusionBot = diffusionBot;
        this.cols = diffusionBot.getCols();
        this.rows = diffusionBot.getRows();
        this.scoutDiffusionMap = new long[cols][rows];
        this.coverageDiffusionMap = new long[cols][rows];
        this.foodDiffusionMap = new long[cols][rows];
        this.enemyDiffusionMap = new long[cols][rows];
        this.enemyHillDiffusionMap = new long[cols][rows];
        this.mixedDiffusionMap = new long[cols][rows];
    }

    public void calcDiffusionMaps(int rounds) {
        DiffusionCalculator scoutCalc = new DiffusionCalculator(
                scoutDiffusionMap,
                diffusionBot.getWaterMap(),
                diffusionBot.getDiscoveredMap(),
                diffusionBot.getMyAntsMap(),
                true
        );
        scoutCalc.calcDiffusionMap(rounds);

        DiffusionCalculator coverageCalc = new DiffusionCalculator(
                coverageDiffusionMap,
                diffusionBot.getWaterMap(),
                diffusionBot.getVisibleMap(),
                diffusionBot.getMyAntsMap(),
                true
        );
        coverageCalc.calcDiffusionMap(rounds);

        DiffusionCalculator foodCalc = new DiffusionCalculator(
                foodDiffusionMap,
                diffusionBot.getWaterMap(),
                diffusionBot.getPossibleFoodMap(),
                diffusionBot.getMyAntsMap()
        );
        foodCalc.calcDiffusionMap(rounds);

        DiffusionCalculator enemyCalc = new DiffusionCalculator(
                enemyDiffusionMap,
                diffusionBot.getWaterMap(),
                diffusionBot.getEnemyAntsMap()
        );
        enemyCalc.calcDiffusionMap(rounds);

        DiffusionCalculator enemyHillCalc = new DiffusionCalculator(
                enemyHillDiffusionMap,
                diffusionBot.getWaterMap(),
                diffusionBot.getPossibleEnemyHillMap()
        );
        enemyHillCalc.calcDiffusionMap(rounds);

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                double food = foodDiffusionMap[c][r] * 0.30;
                double coverage = coverageDiffusionMap[c][r] * 0.10;
                double enemy = enemyDiffusionMap[c][r] * 0.50;
                double scout = scoutDiffusionMap[c][r] * 0.10;

                mixedDiffusionMap[c][r] = Math.round(food + coverage + enemy + scout);

                if (diffusionBot.getInRangeMap()[c][r]) {
                    int nrOfAnts = diffusionBot.getMyAnts().size();
                    if (nrOfAnts < 10) {
                        mixedDiffusionMap[c][r] = 0;
                    } else if (nrOfAnts < 20) {
                        mixedDiffusionMap[c][r] /= 0x00FFFFFF;
                    } else if (nrOfAnts < 25) {
                        mixedDiffusionMap[c][r] /= 0x0000FFFF;
                    } else if (nrOfAnts < 30) {
                        mixedDiffusionMap[c][r] /= 0x000000FF;
                    }
                }
            }
        }
    }

    public long[][] getEnemyHillDiffusionMap() {
        return enemyHillDiffusionMap;
    }

    public long[][] getMixedDiffusionMap() {
        return mixedDiffusionMap;
    }
}
