package BattleArena;

import Droids.Droid;

public class TurnResult {
    private final ActionType action;
    private final int value;
    private final Droid target;

    public TurnResult(ActionType action, int value, Droid target) {
        this.action = action;
        this.value = value;
        this.target = target;
    }
    public TurnResult(ActionType action, int value) {
        this.action = action;
        this.value = value;
        this.target = null;
    }

    public Droid getTarget() {
        return target;
    }

    public ActionType getAction() {
        return action;
    }

    public int getValue() {
        return value;
    }
}
