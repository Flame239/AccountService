package com.flame239.dumbaccountservice.entities.converters;

import com.flame239.dumbaccountservice.entities.CustomerStatus;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.flame239.dumbaccountservice.entities.CustomerStatus.ACTIVE;
import static com.flame239.dumbaccountservice.entities.CustomerStatus.BLOCKED;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CustomerStatusConverterTest {
    private static CustomerStatusConverter converter = new CustomerStatusConverter();

    @Test
    public void toDatabaseColumn() {
        List<CustomerStatus> statuses = Arrays.asList(ACTIVE, BLOCKED);
        List<String> converted = statuses.stream().map(converter::convertToDatabaseColumn).collect(Collectors.toList());
        assertThat(converted, is(Arrays.asList("A", "B")));
    }

    @Test
    public void toEntityAttribute() {
        List<String> dbStatuses = Arrays.asList("A", "B");
        List<CustomerStatus> converted = dbStatuses.stream().map(converter::convertToEntityAttribute).collect(Collectors.toList());
        assertThat(converted, is(Arrays.asList(ACTIVE, BLOCKED)));
    }
}