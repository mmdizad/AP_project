package Controller;

import Model.DuelModel;

import java.io.IOException;

public class StandByPhaseController extends DuelController {
    DuelModel duelModel = duelController.duelModel;

    public Integer hasSpellEffectInThisPhase() {
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("hasSpellEffectInStandByPhase" + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return Integer.parseInt(response);
    }

    public String effectOfSpellInThisPhase(int response) {
        String result = "";
        try {
            LoginController.dataOutputStream.writeUTF("effectOfSpellInStandByPhase" + "/" + response + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            result = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return result;
    }
}