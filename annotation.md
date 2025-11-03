# Java Annotation

각 annotation은 'annotation interface'인 `@interface` 문법으로 만들어져야 한다. annotation의 method는 곧 annotation을 사용할 때 상응하는 요소 중 하나가 된다. 가령, JUnit `RepeatedTest`는 다음과 같다.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatedTest {
    int failureThreshold();
    . . .
}
```
annotation process는 `@RepeatedTest`가 붙은 object를 보고 annotation에 설정된 `failureThreshold` 메서드를 호출하여 설정한 값을 얻어내는 것이다. 참고로 이 annotation interface안의 메서드는 파라미터를 받을 수 없고, `throws`을 못붙이고 generic 대상이 되지 않는다.

annotation interface를 만들 때는 해당 annotation interface이 어디까지 접근할 지, 언제 처리될 지에 대한 정의를 내려줘야 한다. 그것이 바로 `@Target`과 `@Retention`이다. 

1. `@Target`: 해당 annotation interface를 적용시킬 범위를 지정한다. 위 처럼 `METHOD`인 경우 메서드에만 붙일 수 있다. 만약 여러 곳에 붙이고 싶다면 다음과 같이 쓸 수 있다.
```java
@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.TYPE})
public @interface ToString
```

위와 같이 여러 field들을 지정할 수 있다. 만약, 지정한 field가 아니라 다른 곳에 사용하면 컴파일 시간에 에러가 발생한다. 

`@Target`에 사용되는 element type은 다음과 같다.

| 값                  | 적용 대상                    | 예시                                      |
| ------------------ | ------------------------ | --------------------------------------- |
| `TYPE`             | 클래스, 인터페이스, enum, record | `@MyAnno class Foo {}`                  |
| `FIELD`            | 클래스의 멤버 변수               | `@MyAnno private int id;`               |
| `METHOD`           | 메서드                      | `@MyAnno void run() {}`                 |
| `PARAMETER`        | 메서드의 파라미터                | `void foo(@MyAnno String arg)`          |
| `CONSTRUCTOR`      | 생성자                      | `@MyAnno MyClass() {}`                  |
| `LOCAL_VARIABLE`   | 지역 변수                    | `void m() { @MyAnno int x = 0; }`       |
| `ANNOTATION_TYPE`  | 어노테이션 자체                 | `@MyAnno @interface OtherAnno {}`       |
| `PACKAGE`          | 패키지 선언                   | `@MyAnno package com.example;`          |
| `TYPE_PARAMETER`   | 제네릭 타입 파라미터              | `class Box<@MyAnno T> {}`               |
| `TYPE_USE`         | 타입이 사용되는 모든 곳 (Java 8+)  | `List<@MyAnno String> list;`            |
| `MODULE`           | 모듈 선언 (Java 9+)          | `@MyAnno module my.module {}`           |
| `RECORD_COMPONENT` | record 컴포넌트 (Java 16+)   | `record Point(@MyAnno int x, int y) {}` |

가령, `@Target(ElementType.TYPE)`을 설정하면 해당 annotation interface는 class와 enum, record에 붙일 수 있게 된다.

annotation interface의 element에 default 요소를 정의하는 메서드를 뒤에 절에 추가할 수 있다.

```java
public @interface RepeatedTest {
    int failureThreshold() default Integer.MAX_VALUE;
    . . .
}
```

아래는 default로 빈 배열 또는 default annotation을 가지는 예제이다. 참고로, annotation은 다른 annotation을 요소로 가질 수 있다.
```java
public @interface BugReport {
    String[] reportedBy() default {};
        // Defaults to empty array
    Reference ref() default @Reference(id=0);
        // Default for an annotation
    . . .
}
```

모든 annotation은 암시적으로 `java.lang.annotation.Annotation` interface을 확장한다. `Annotation`은 regular interface이지 annotation interface가 아니다. 참고로, annotation interface는 의도적으로 확장할 수 없으며, annotation interface를 구현하는 클래스를 제공할 수도 없다. 대신 source 처리 도구와 가상 머신은 필요에 따라 proxy class와 객체를 생성하도록 한다.


2. `@Retention`: annotation이 언제까지 유지될 지를 결정한다. 컴파일 전까지인 `SOURCE`와 클래스 파일까지인 `CLASS`, JVM 실행 중에도 리플렉션으로 접근 가능한 `RUNTIME`이 있다.

대부분의 custom annotation은 JVM 런타임 중에도 접근 가능하도록 `RUNTIME`을 사용하여 파싱을 진행한다. 이때 사용되는 핵심 메서드들은 다음과 같다.

```java
T getAnnotation(Class<T>)
T getDeclaredAnnotation(Class<T>)
T[] getAnnotationsByType(Class<T>)
T[] getDeclaredAnnotationsByType(Class<T>)
Annotation[] getAnnotations()
Annotation[] getDeclaredAnnotations()
boolean isAnnotationPresent(Class<? extends Annotation> annotationClass)
```
이는 `AnnotatedElement`의 메서드들로 `Class`, `Field`, `Parameter`, `Method`, `Constrcutor` 등이 해당 interface를 구현한다.

다음과 같이 특정 객체의 class에 소속된 annotation을 불러낼 수 있다.
```java
Class<?> cl = obj.getClass();
ToString ts = cl.getAnnotation(ToString.class);
if (ts != null && ts.includeName()) . . .
```


