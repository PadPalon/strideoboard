package ch.neukom.strideoboard.validation;

import java.util.Optional;

/**
 * validators to run before start of vlc
 */
public interface Validator {
    /**
     * run validation
     * @return {@link Optional#empty()} if no error occured, filled otherwise
     */
    Optional<String> validate();
}
