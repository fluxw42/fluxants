package info.fluxprojects.fluxant.diffusion;

import info.fluxprojects.fluxant.core.*;
import info.fluxprojects.fluxant.utils.MapUtils;

import java.util.HashSet;
import java.util.Set;

public class DiffusionBot extends AbstractBot {

    DiffusionMaps diffusionMaps = null;

    @Override
    public Set<Order> doTurn() {
        HashSet<Order> orders = new HashSet<Order>();
        getDiffusionMaps().calcDiffusionMaps(25);

        for (Ant ant : getMyAnts()) {
            Direction bestMove;

            if (!isPossibleEnemyHillVisible() || ((ant.hashCode() % 2) == 0) || (getMyAnts().size() < 10)) {
                bestMove = MapUtils.getBestMove(getDiffusionMaps().getMixedDiffusionMap(), ant.getPosition().getCol(), ant.getPosition().getRow());
            } else {
                long[][] enemyHillDiffusionMap = getDiffusionMaps().getEnemyHillDiffusionMap();
                bestMove = MapUtils.getBestMove(enemyHillDiffusionMap, ant.getPosition().getCol(), ant.getPosition().getRow());
            }

            if (bestMove != null) {
                orders.add(new Order(ant.getPosition(), bestMove));
            }
        }

        return orders;
    }

    public DiffusionMaps getDiffusionMaps() {
        if (diffusionMaps == null) {
            diffusionMaps = new DiffusionMaps(this);
        }
        return diffusionMaps;
    }
}
