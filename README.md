cantaa-commons
==============
Release:

1.7-SNAPSHOT (start 19.03.2015)
- Wicket: New FocusOnLoadBehavior

1.6
- Start Bundle-Externalization
- new EnumUtil + ValueHolder
- Wicket AbstractApplicationFactory no longer writes the appType to a system property but uses a static variable in class Environment

1.5
- Time, TimeConverter
- PlaceHolderConfigurer, Prop.loader
- Cache: Multikey-Aware
- BooleanModel
- JQuery/Ajax-Wicket
- Convenient Generic Comparator
- Dependencies set to optional

1.2-SNAPSHOT
  - new PercentageConverter (like MoneyConverter)
  - Types Money, Percentage, and Quantity registered in SafeModel

1.1
  First release to Maven Central

External dependancies:
- Global
  * slf-log4j12
  * junit4 (Test only)

- Spring-Package
  * spring-core, -beans, -web, -orm
  * javax.persistence

- Wicket-Package
  * wicket-core:
  * wicket-spring:
  * javassist (SafeModel)

All dependencies are marked as optional