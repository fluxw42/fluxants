package info.fluxprojects.fluxant.core;

import java.util.*;

import static info.fluxprojects.fluxant.utils.MapUtils.getCorrectedCol;
import static info.fluxprojects.fluxant.utils.MapUtils.getCorrectedRow;

public abstract class AbstractBot extends AbstractSystemInputParser {

    private long turnStartTime;
    private int loadTime;
    private int turnTime;
    private int rows;
    private int cols;
    private int turns;
    private int viewRadius;
    private int attackRadius;
    private int spawnRadius;
    private int turn;

    private boolean[][] waterMap;
    private boolean[][] myHillMap;
    private boolean[][] enemyHillMap;
    private boolean[][] foodMap;
    private boolean[][] possibleFoodMap;
    private boolean[][] possibleEnemyHillMap;
    private boolean[][] myAntsMap;
    private boolean[][] enemyAntsMap;
    private boolean[][] discoveredMap;
    private boolean[][] visibleMap;
    private boolean[][] inRangeMap;

    private final Set<Position> possibleFood = new HashSet<Position>();
    private final Set<Position> possibleEnemyHill = new HashSet<Position>();

    private boolean fullyDiscovered = false;
    private boolean foodVisible = false;
    private boolean enemyHillVisible = false;
    private boolean enemyVisible = false;
    private boolean possibleFoodVisible = false;
    private boolean possibleEnemyHillVisible = false;

    private Set<Ant> myAnts = new HashSet<Ant>();

    @Override
    public void setup(int loadTime, int turnTime, int rows, int cols, int turns, int viewRadius, int attackRadius, int spawnRadius) {
        this.loadTime = loadTime;
        this.turnTime = turnTime;
        this.rows = rows;
        this.cols = cols;
        this.turns = turns;
        this.viewRadius = viewRadius;
        this.attackRadius = attackRadius;
        this.spawnRadius = spawnRadius;
        this.turn = 0;
        initialize();
    }

    public void initialize() {
        clearWaterMap();
        clearVisibleMap();
        clearInRangeMap();
        clearDiscoveredMap();
        clearEnemyHillMap();
        clearMyHillMap();
        clearPossibleFoodMap();
        clearPossibleEnemyHillMap();
        clearMyAntsMap();
        clearEnemyAntsMap();
        clearFoodMap();
    }

    public long getTurnStartTime() {
        return turnStartTime;
    }

    public int getTurnsLeft() {
        return getTurns() - getTurn();
    }

    public int getTurn() {
        return turn;
    }

    public int getLoadTime() {
        return loadTime;
    }

    public int getTurnTime() {
        return turnTime;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTurns() {
        return turns;
    }

    public int getViewRadius() {
        return viewRadius;
    }

    public int getAttackRadius() {
        return attackRadius;
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public int getTurnTimeLeft() {
        long timePassed = System.currentTimeMillis() - getTurnStartTime();
        return (int) (getTurnTime() - timePassed);
    }

    @Override
    public void beforeUpdate() {
        this.turnStartTime = System.currentTimeMillis();
        this.turn++;
        this.enemyHillVisible = false;
        this.enemyVisible = false;
        this.foodVisible = false;
        this.possibleFoodVisible = false;
        this.possibleEnemyHillVisible = false;
        clearFoodMap();
        clearEnemyHillMap();
        clearMyAntsMap();
        clearEnemyAntsMap();
    }

    @Override
    public void afterUpdate() {
        updateVisibleMap();
        updateInRangeMap();
        updateDiscoveredMap();
        updatePossibleFoodMap();
        updatePossibleEnemyHillMap();

        for (Ant ant : myAnts) {
            System.err.println(ant);
        }

    }

    private void updatePossibleFoodMap() {
        for (int c = 0; c < getCols(); c++) {
            for (int r = 0; r < getRows(); r++) {
                boolean hasFood = getFoodMap()[c][r];
                if (getVisibleMap()[c][r]) {
                    if (hasFood) {
                        getPossibleFoodMap()[c][r] = true;
                        possibleFood.add(new Position(c, r));
                    } else {
                        getPossibleFoodMap()[c][r] = false;
                        possibleFood.remove(new Position(c, r));
                    }
                } else if (hasFood) {
                    getPossibleFoodMap()[c][r] = true;
                    possibleFood.add(new Position(c, r));
                }
                if (possibleFoodMap[c][r]) {
                    this.possibleFoodVisible = true;
                }
            }
        }
    }

    private void updatePossibleEnemyHillMap() {
        for (int c = 0; c < getCols(); c++) {
            for (int r = 0; r < getRows(); r++) {
                boolean hasHill = getEnemyHillMap()[c][r];
                if (getVisibleMap()[c][r]) {
                    if (hasHill) {
                        getPossibleEnemyHillMap()[c][r] = true;
                        possibleEnemyHill.add(new Position(c, r));
                    } else {
                        getPossibleEnemyHillMap()[c][r] = false;
                        possibleEnemyHill.remove(new Position(c, r));
                    }
                } else if (hasHill) {
                    getPossibleEnemyHillMap()[c][r] = true;
                    possibleEnemyHill.add(new Position(c, r));
                }
                if (possibleEnemyHillMap[c][r]) {
                    this.possibleEnemyHillVisible = true;
                }
            }
        }
    }

    private void updateVisibleMap() {
        clearVisibleMap();

        for (int c = 0; c < getCols(); c++) {
            for (int r = 0; r < getRows(); r++) {
                if (myAntsMap[c][r]) {
                    int mx = (int) Math.sqrt(getViewRadius());
                    for (int row = -mx; row <= mx; ++row) {
                        for (int col = -mx; col <= mx; ++col) {
                            int d = row * row + col * col;
                            if (d <= getViewRadius()) {
                                int correctedCol = getCorrectedCol(c + col, getCols());
                                int correctedRow = getCorrectedRow(r + row, getRows());
                                visibleMap[correctedCol][correctedRow] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateInRangeMap() {
        clearInRangeMap();

        for (int c = 0; c < getCols(); c++) {
            for (int r = 0; r < getRows(); r++) {
                if (enemyAntsMap[c][r]) {
                    int mx = (int) Math.sqrt(getAttackRadius() + 5);
                    for (int row = -mx; row <= mx; ++row) {
                        for (int col = -mx; col <= mx; ++col) {
                            int d = row * row + col * col;
                            if (d <= getViewRadius()) {
                                int correctedCol = getCorrectedCol(c + col, getCols());
                                int correctedRow = getCorrectedRow(r + row, getRows());
                                inRangeMap[correctedCol][correctedRow] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateDiscoveredMap() {
        boolean allDiscovered = true;
        for (int c = 0; c < getCols(); c++) {
            for (int r = 0; r < getRows(); r++) {
                discoveredMap[c][r] |= visibleMap[c][r];
                allDiscovered &= discoveredMap[c][r];
            }
        }
        this.fullyDiscovered = allDiscovered;
    }

    private void clearWaterMap() {
        this.waterMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : waterMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearDiscoveredMap() {
        this.discoveredMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : discoveredMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearEnemyHillMap() {
        this.enemyHillMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : enemyHillMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearMyHillMap() {
        this.myHillMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : myHillMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearFoodMap() {
        this.foodMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : foodMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearPossibleFoodMap() {
        this.possibleFood.clear();
        this.possibleFoodMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : possibleFoodMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearPossibleEnemyHillMap() {
        this.possibleEnemyHill.clear();
        this.possibleEnemyHillMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : possibleEnemyHillMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearMyAntsMap() {
        this.myAntsMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : myAntsMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearEnemyAntsMap() {
        this.enemyAntsMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : enemyAntsMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearVisibleMap() {
        this.visibleMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : visibleMap) {
            Arrays.fill(completeRow, false);
        }
    }

    private void clearInRangeMap() {
        this.inRangeMap = new boolean[getCols()][getRows()];
        for (boolean[] completeRow : inRangeMap) {
            Arrays.fill(completeRow, false);
        }
    }

    public boolean[][] getWaterMap() {
        if (waterMap == null) {
            clearWaterMap();
        }
        return waterMap;
    }

    public boolean[][] getMyHillMap() {
        if (myHillMap == null) {
            clearMyHillMap();
        }
        return myHillMap;
    }

    public boolean[][] getEnemyHillMap() {
        if (enemyHillMap == null) {
            clearEnemyHillMap();
        }
        return enemyHillMap;
    }

    public boolean[][] getFoodMap() {
        if (foodMap == null) {
            clearFoodMap();
        }
        return foodMap;
    }

    public boolean[][] getPossibleFoodMap() {
        if (possibleFoodMap == null) {
            clearPossibleFoodMap();
        }
        return possibleFoodMap;
    }

    public boolean[][] getPossibleEnemyHillMap() {
        if (possibleEnemyHillMap == null) {
            clearPossibleEnemyHillMap();
        }
        return possibleEnemyHillMap;
    }

    public boolean[][] getMyAntsMap() {
        if (myAntsMap == null) {
            clearMyAntsMap();
        }
        return myAntsMap;
    }

    public boolean[][] getEnemyAntsMap() {
        if (enemyAntsMap == null) {
            clearEnemyAntsMap();
        }
        return enemyAntsMap;
    }

    public boolean[][] getDiscoveredMap() {
        if (discoveredMap == null) {
            clearDiscoveredMap();
        }
        return discoveredMap;
    }

    public boolean[][] getVisibleMap() {
        if (visibleMap == null) {
            clearVisibleMap();
        }
        return visibleMap;
    }

    public boolean[][] getInRangeMap() {
        if (inRangeMap == null) {
            clearInRangeMap();
        }
        return inRangeMap;
    }

    public boolean isFullyDiscovered() {
        return fullyDiscovered;
    }

    public boolean isFoodVisible() {
        return foodVisible;
    }

    public boolean isEnemyHillVisible() {
        return enemyHillVisible;
    }

    public boolean isEnemyVisible() {
        return enemyVisible;
    }

    public Set<Ant> getMyAnts() {
        return myAnts;
    }

    public Set<Position> getPossibleFood() {
        return possibleFood;
    }

    public Set<Position> getPossibleEnemyHill() {
        return possibleEnemyHill;
    }

    @Override
    public void addWater(int row, int col) {
        waterMap[col][row] = true;
    }

    @Override
    public void addFood(int row, int col) {
        this.foodVisible = true;
        foodMap[col][row] = true;
    }

    @Override
    public void addAnt(int row, int col, int owner) {
        if (owner == 0) {
            myAntsMap[col][row] = true;
            Position position = new Position(col, row);
            for (Ant myAnt : myAnts) {
                if (myAnt.getPosition().equals(position)) {
                    return;
                }
            }
            Ant myAnt = new Ant(position);
            myAnts.add(myAnt);
        } else {
            enemyAntsMap[col][row] = true;
            enemyVisible = true;
        }
    }

    @Override
    public void removeAnt(int row, int col, int owner) {
        if (owner == 0) {
            Position position = new Position(col, row);
            for (Ant myAnt : myAnts) {
                Position pos = myAnt.getPosition();
                if ((pos != null) && (pos.equals(position))) {
                    myAnts.remove(myAnt);
                    return;
                }
            }
        }
    }

    @Override
    public void addHill(int row, int col, int owner) {
        if (owner == 0) {
            myHillMap[col][row] = true;
        } else {
            this.enemyHillVisible = true;
            enemyHillMap[col][row] = true;
        }
    }

    @Override
    protected Set<Order> validateOrders(Set<Order> orders) {
        Set<Order> validOrders = new HashSet<Order>();
        Set<Position> occupied = new HashSet<Position>();
        for (Order order : orders) {
            Position newPosition = order.getNewPosition(getCols(), getRows());
            if (isValidOrder(order) && !occupied.contains(newPosition)) {
                occupied.add(newPosition);
                validOrders.add(order);
            }
        }
        return validOrders;
    }

    private boolean isValidOrder(Order order) {
        Position newPosition = order.getNewPosition(getCols(), getRows());
        int c = newPosition.getCol();
        int r = newPosition.getRow();

        boolean myAntOnStart = myAntsMap[order.getCol()][order.getRow()];
        boolean noWater = !waterMap[c][r];
        boolean noAntOnDestination = !myAntsMap[c][r];
        boolean noFood = !foodMap[c][r];

        return myAntOnStart && noWater && noAntOnDestination && noFood;
    }

    @Override
    protected void afterTurn(Set<Order> orders) {
        for (Order order : orders) {
            for (Ant myAnt : myAnts) {
                if (myAnt.getPosition().equals(order.getOldPosition())) {
                    myAnt.setPosition(order.getNewPosition(getCols(), getRows()));
                    break;
                }
            }
        }
    }

    public boolean isPossibleFoodVisible() {
        return possibleFoodVisible;
    }

    public boolean isPossibleEnemyHillVisible() {
        return possibleEnemyHillVisible;
    }

}
