package com.sacidpak.courier.tracking.config;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

  private static final String DEFAULT_LOCALE = "en-EN";
  private static final List<Locale> LOCALES = Arrays.asList(Locale.forLanguageTag("en"), Locale.forLanguageTag("tr"));

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    if (StringUtils.isEmpty(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE))) {
      return Locale.forLanguageTag(DEFAULT_LOCALE);
    }
    var list = Locale.LanguageRange.parse(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
    return Locale.lookup(list, LOCALES);
  }
}
