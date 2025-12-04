package com.meetup.whatdo.common.utils;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Base62IdArgumentResolver implements HandlerMethodArgumentResolver {

    private final TsidBase62Util tsidBase62Util;

    public Base62IdArgumentResolver(TsidBase62Util tsidBase62Util) {
        this.tsidBase62Util = tsidBase62Util;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Base62Id.class)
                && parameter.getParameterType().isAssignableFrom(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        /*@SuppressWarnings("unchecked")
        Map<String, String> uriTemplateVars =
                (Map<String, String>) webRequest.getAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                        NativeWebRequest.SCOPE_REQUEST);

         */

        Object attr = webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                NativeWebRequest.SCOPE_REQUEST);

        Map<String, String> uriTemplateVars = Collections.emptyMap();
        if (attr instanceof Map<?,?> map) {
            uriTemplateVars = map.entrySet().stream()
                    .filter(e -> e.getKey() instanceof String && e.getValue() instanceof String)
                    .collect(Collectors.toMap(
                            e -> (String) e.getKey(),
                            e -> (String) e.getValue()
                    ));
        }
        String varName = parameter.getParameterName();
        String value = uriTemplateVars.get(varName);
        if (value == null) return null;

        return TsidBase62Util.decode(value);

    }

    public TsidBase62Util getTsidBase62Util() {
        return tsidBase62Util;
    }
}
