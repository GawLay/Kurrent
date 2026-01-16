# Keep all domain models
-keep class test.kyrie.core.domain.model.** { *; }

# Keep repository interfaces
-keep interface test.kyrie.core.domain.repository.** { *; }

# Keep use cases
-keep class test.kyrie.core.domain.usecase.** { *; }

# Keep Result class and its sealed subclasses
-keep class test.kyrie.core.domain.util.Result { *; }
-keep class test.kyrie.core.domain.util.Result$** { *; }

