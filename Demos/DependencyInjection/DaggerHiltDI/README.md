# Dependency Injection

Class from [Dependency Injection in Android with Dagger 2 and Hilt](https://www.udemy.com/course/dependency-injection-in-android-with-dagger/) course by [Vasiliy Zukanov](https://www.udemy.com/user/vasiliy-zukanov/).

### Dagger Conventions

- Components and Modules
  - Components are interfaces annotated with @Component
  - Modules are classes annotated with @Module
  - Methods in modules that provide services are annotated with @Provides
  - Provided services can be used as method arguments in other provider methods
- Scopes
  - Scopes are annotations, annotated with @Scope
  - Components that provide scoped services must be scoped
  - All clients get the same instance of a scoped service **from the same instance** of a Component
- Component as injector
  - Void methods with single argument defined on components generate injectors for the type of the argument
  - Client's non-privative non-final properties (fields) annotated with @Inject designate injection targets
- Dependent Components
  - Component inter-dependencies are specified as part of @Component annotation
  - Component B that depends on Component A has implicit access to all services exposed by Component A
    - Services from A can be injected by B
    - Services from A can be consumed inside modules of B
- Subcomponents
  - Subcomponents are specified by @Subcomponent annotation
  - Parent Component exposes factory method which returns Subcomponent
  - The argument of the factory method are Subcomponent's modules
  - Subcomponents get access to all services provided by parent (provided, not just exposed)
- Multi-Module Components
  - Components can use more than one module
  - Modules of a single Component share the same object graph
  - Dagger automatically instantiates modules with no-argument constructors
- Automatic Service Discovery (@Inject annotated constructors)
  - Dagger can automatically discover services having a public constructor annotated with @Inject annotation
  - Automatically discovered services can be scoped
