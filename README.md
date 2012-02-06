Overview

This is a simple extension the Stripes Framework (http://stripesframework.org/display/stripes/Home)
to allow for control over most, if not all, Stripes resources though CDI.

Currently we rely in the latest source from Stripes (1.6.0-SNAPSHOT) as it provides
all the necessary integration points to hook our claws into and control just
about every aspect of the application.

Currently Tested

  * Simple Action Bean injection and control. Both RequestScoped, and Dependent scoped beans. (SessionScoped should be fine, just have not tested)
  * URL mapping, lookup, and generation of CDI managed Action beans.
  * RequestScoped variables and validation within ActionBeans (models).