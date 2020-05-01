package com.nokchax.escape.command;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.command.commands.HelpCommand;
import com.nokchax.escape.entry.repository.EntryRepository;
import com.nokchax.escape.mission.repository.MissionRepository;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class CommandMakerTest extends ServiceLayerTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CommandMaker commandMaker;

    @Test
    void initTest() {
        Message mockMessage = mock(Message.class);

        when(mockMessage.getText()).thenReturn("help");

        Command<?> command = commandMaker.makeCommand(mockMessage);

        assertThat(command).isNotNull();
        assertThat(command.process()).endsWith("/todo");
        System.out.println("command = " + command.process());
    }

    @Test
    @DisplayName("애플리케이션 컨텍스트로 부터 클래스를 가지고 빈을 잘 가져오는지 테스트")
    void reflectionTest() {
        log.info("Get beans start");
        Map<Class<?>, Object> collect = new HashMap<>();
        Stream.of(UserRepository.class, MissionRepository.class, EntryRepository.class, ProblemRepository.class)
                .forEach(clazz -> collect.put(clazz, applicationContext.getBean(clazz)));

        assertThat(collect.size()).isNotZero();
        collect.forEach((k, v) -> System.out.println(k + " : " + v));

        log.info("Get beans end");
    }

    @Test
    @DisplayName("map으로 부터 클래스를 받아와서 생성자 호출하기!")
    void callConstructorWithReflection() {
        Map<String, Class<? extends Command<?>>> classes = new HashMap<>();

        classes.put("commandType", HelpCommand.class);

        try {
            classes.get("commandType")
                    .getConstructor(Message.class, ApplicationContext.class)
                    .newInstance(new Message(), new ApplicationContext() {
                @Override
                public String getId() {
                    return null;
                }

                @Override
                public String getApplicationName() {
                    return null;
                }

                @Override
                public String getDisplayName() {
                    return null;
                }

                @Override
                public long getStartupDate() {
                    return 0;
                }

                @Override
                public ApplicationContext getParent() {
                    return null;
                }

                @Override
                public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
                    return null;
                }

                @Override
                public BeanFactory getParentBeanFactory() {
                    return null;
                }

                @Override
                public boolean containsLocalBean(String name) {
                    return false;
                }

                @Override
                public boolean containsBeanDefinition(String beanName) {
                    return false;
                }

                @Override
                public int getBeanDefinitionCount() {
                    return 0;
                }

                @Override
                public String[] getBeanDefinitionNames() {
                    return new String[0];
                }

                @Override
                public String[] getBeanNamesForType(ResolvableType type) {
                    return new String[0];
                }

                @Override
                public String[] getBeanNamesForType(ResolvableType type, boolean includeNonSingletons, boolean allowEagerInit) {
                    return new String[0];
                }

                @Override
                public String[] getBeanNamesForType(Class<?> type) {
                    return new String[0];
                }

                @Override
                public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
                    return new String[0];
                }

                @Override
                public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
                    return null;
                }

                @Override
                public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
                    return null;
                }

                @Override
                public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
                    return new String[0];
                }

                @Override
                public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
                    return null;
                }

                @Override
                public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
                    return null;
                }

                @Override
                public Object getBean(String name) throws BeansException {
                    return null;
                }

                @Override
                public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
                    return null;
                }

                @Override
                public Object getBean(String name, Object... args) throws BeansException {
                    return null;
                }

                @Override
                public <T> T getBean(Class<T> requiredType) throws BeansException {
                    return null;
                }

                @Override
                public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
                    return null;
                }

                @Override
                public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
                    return null;
                }

                @Override
                public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
                    return null;
                }

                @Override
                public boolean containsBean(String name) {
                    return false;
                }

                @Override
                public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
                    return false;
                }

                @Override
                public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
                    return false;
                }

                @Override
                public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
                    return false;
                }

                @Override
                public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
                    return false;
                }

                @Override
                public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
                    return null;
                }

                @Override
                public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
                    return null;
                }

                @Override
                public String[] getAliases(String name) {
                    return new String[0];
                }

                @Override
                public void publishEvent(Object event) {

                }

                @Override
                public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
                    return null;
                }

                @Override
                public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
                    return null;
                }

                @Override
                public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
                    return null;
                }

                @Override
                public Environment getEnvironment() {
                    return null;
                }

                @Override
                public Resource[] getResources(String locationPattern) throws IOException {
                    return new Resource[0];
                }

                @Override
                public Resource getResource(String location) {
                    return null;
                }

                @Override
                public ClassLoader getClassLoader() {
                    return null;
                }
            })
                    .process();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Annotation 기반으로 클래스를 찾아서 map 초기화하기")
    void initMapWithAnnotationAttachedClasses() {
        //https://stackoverflow.com/a/47428495
        //https://stackoverflow.com/a/23973331
    }
}