package businessLogic;

import exceptions.BusinessLogicException;


public interface ESportsManager {
    // Métodos de la interfaz
    public void passwordRecovery(String mail) throws BusinessLogicException;
}