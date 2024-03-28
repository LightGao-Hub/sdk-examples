package org.example.sdk.app.common.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

public class ConditionalOnBeanClassCondition implements Condition {
    /**
     * 判断是否满足特定条件，用于`@ConditionalOnBeanClass`注解。根据配置的类名和值进行匹配。
     *
     * @param context  用于获取环境信息的条件上下文
     * @param metadata 提供元数据信息的类型元数据
     * @return 如果类名对应的环境属性值等于`havingValue`，返回`true`；如果环境属性值为空且`matchIfMissing`为`true`，也返回`true`；否则返回`false`
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 从元数据中获取`ConditionalOnBeanClass`注解的属性值
        Class<?>[] annotationValues = (Class<?>[]) metadata.getAnnotationAttributes(ConditionalOnBeanClass.class.getName()).get("name");
        String annotationClassName = annotationValues[0].getName(); // 获取类的全限定名
        String havingValue = (String) metadata.getAnnotationAttributes(ConditionalOnBeanClass.class.getName()).get("havingValue");
        boolean matchIfMissing = (boolean) metadata.getAnnotationAttributes(ConditionalOnBeanClass.class.getName()).get("matchIfMissing");

        // 从环境获取配置的类名对应的值
        String propertyValue = context.getEnvironment().getProperty(annotationClassName);

        // 如果配置值存在且等于`havingValue`，返回`true`
        if (StringUtils.hasText(propertyValue)) {
            return havingValue.equals(propertyValue);
        } else { // 如果配置值不存在，根据`matchIfMissing`决定是否匹配
            return matchIfMissing;
        }
    }


}

