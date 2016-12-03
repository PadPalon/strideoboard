package ch.neukom.strideoboard.validation;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * runs several defined {@link Validator} at the same time
 */
public class ValidationHelper {
    private ValidationHelper() {
    }

    public static List<String> validate() {
        List<Validator> validators = ImmutableList.of(
                new VlcValidator()
        );

        return validators.stream()
                .map(Validator::validate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
