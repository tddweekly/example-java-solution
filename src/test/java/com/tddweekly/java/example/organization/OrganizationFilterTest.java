package com.tddweekly.java.example.organization;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

import static com.tddweekly.java.example.organization.OrganizationFilter.TIER_HEADER;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class OrganizationFilterTest {
    @ParameterizedTest(name = "header: {0}")
    @NullAndEmptySource
    @ValueSource(strings = "foo")
    void testWrongHeaderValue_doFilterInternal_setsFreeAsDefault(String headerValue) throws ServletException, IOException {
        // given
        var request = mock(HttpServletRequest.class);
        given(request.getHeader(TIER_HEADER)).willReturn(headerValue);
        // and
        var response = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);
        // and
        var service = new OrganizationServiceImpl();
        var toTest = new OrganizationFilter(service);

        // when
        toTest.doFilterInternal(request, response, chain);

        // then
        then(chain).should().doFilter(request, response);
        assertThat(service.current()).extracting("tier").isEqualTo(Organization.Tier.FREE);
    }

    @ParameterizedTest(name = "header: {0}")
    @EnumSource(Organization.Tier.class)
    void testHeaderAsEnum_doFilterInternal_setsFromHeader(Organization.Tier tier) throws ServletException, IOException {
        // given
        var request = mock(HttpServletRequest.class);
        given(request.getHeader(TIER_HEADER)).willReturn(tier.name());
        // and
        var response = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);
        // and
        var service = new OrganizationServiceImpl();
        var toTest = new OrganizationFilter(service);

        // when
        toTest.doFilterInternal(request, response, chain);

        // then
        then(chain).should().doFilter(request, response);
        assertThat(service.current()).extracting("tier").isEqualTo(tier);
    }

    @ParameterizedTest(name = "header: {0}")
    @MethodSource("lowercaseEnum")
    void testHeaderAsEnumCaseInsensitive_doFilterInternal_setsFromHeader(String value, Organization.Tier expected) throws ServletException, IOException {
        // given
        var request = mock(HttpServletRequest.class);
        given(request.getHeader(TIER_HEADER)).willReturn(value);
        // and
        var response = mock(HttpServletResponse.class);
        var chain = mock(FilterChain.class);
        // and
        var service = new OrganizationServiceImpl();
        var toTest = new OrganizationFilter(service);

        // when
        toTest.doFilterInternal(request, response, chain);

        // then
        then(chain).should().doFilter(request, response);
        assertThat(service.current()).extracting("tier").isEqualTo(expected);
    }

    static Stream<Arguments> lowercaseEnum() {
        return stream(Organization.Tier.values()).map(tier -> arguments(tier.name().toLowerCase(), tier));
    }
}
