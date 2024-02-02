package businessLogic;

import exceptions.BusinessLogicException;

/**
 * The {@code ESportsManager} interface defines the contract for managing eSports-related functionality,
 * including methods for password recovery.
 * 
 * <p>Implementations of this interface provide the concrete logic for the specified functionality.</p>
 * 
 * <p>Usage example:</p>
 * <pre>{@code
 * try {
 *     ESportsManager manager = // Obtain an instance of ESportsManager implementation
 *     manager.passwordRecovery("user@example.com");
 * } catch (BusinessLogicException ex) {
 *     // Handle exception, e.g., log or display an error message
 * }
 * }</pre>
 * 
 * <p>The {@code passwordRecovery} method is responsible for initiating the password recovery process
 * for the specified email address. It may throw a {@code BusinessLogicException} if there are issues
 * during the recovery process.</p>
 * 
 * <p><strong>Author:</strong> [Author Name]</p>
 * 
 * @see ESportsFactory
 * @see ESportsManagerImplementation
 * @see BusinessLogicException
 */
public interface ESportsManager {
    
    /**
     * Initiates the password recovery process for the specified email address.
     * 
     * <p>This method is responsible for triggering the necessary steps for recovering
     * the password associated with the given email address.</p>
     * 
     * <p>If the recovery process encounters issues or is unsuccessful, a {@code BusinessLogicException}
     * may be thrown to indicate the failure.</p>
     * 
     * @param mail the email address for which password recovery is requested.
     * 
     * @throws BusinessLogicException if there are issues during the password recovery process.
     * 
     * @see BusinessLogicException
     */
    public void passwordRecovery(String mail) throws BusinessLogicException;
}
