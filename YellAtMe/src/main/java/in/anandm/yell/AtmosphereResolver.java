package in.anandm.yell;

import javax.servlet.http.HttpServletRequest;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Meteor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AtmosphereResolver implements HandlerMethodArgumentResolver{
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return AtmosphereResource.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Meteor m = Meteor.build(nativeWebRequest.getNativeRequest(HttpServletRequest.class));
        return m.getAtmosphereResource();
    }
}