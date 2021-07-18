package Controller;

public class ActiveEffectController {
    private static ActiveEffectController activeEffectController;

    private ActiveEffectController() {

    }

    public static ActiveEffectController getInstance() {
        if (activeEffectController == null) {
            activeEffectController = new ActiveEffectController();
        }
        return activeEffectController;
    }
}
