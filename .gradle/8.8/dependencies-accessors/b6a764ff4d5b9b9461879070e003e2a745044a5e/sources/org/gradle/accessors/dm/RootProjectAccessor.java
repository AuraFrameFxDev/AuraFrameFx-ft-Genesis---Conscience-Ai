package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class RootProjectAccessor extends TypeSafeProjectDependencyFactory {


    @Inject
    public RootProjectAccessor(DefaultProjectDependencyFactory factory, ProjectFinder finder) {
        super(factory, finder);
    }

    /**
     * Creates a project dependency on the project at path ":"
     */
    public GenesisOsProjectDependency getGenesisOs() { return new GenesisOsProjectDependency(getFactory(), create(":")); }

    /**
     * Creates a project dependency on the project at path ":app"
     */
    public AppProjectDependency getApp() { return new AppProjectDependency(getFactory(), create(":app")); }

    /**
     * Creates a project dependency on the project at path ":collab-canvas"
     */
    public CollabCanvasProjectDependency getCollabCanvas() { return new CollabCanvasProjectDependency(getFactory(), create(":collab-canvas")); }

    /**
     * Creates a project dependency on the project at path ":colorblendr"
     */
    public ColorblendrProjectDependency getColorblendr() { return new ColorblendrProjectDependency(getFactory(), create(":colorblendr")); }

    /**
     * Creates a project dependency on the project at path ":core-module"
     */
    public CoreModuleProjectDependency getCoreModule() { return new CoreModuleProjectDependency(getFactory(), create(":core-module")); }

    /**
     * Creates a project dependency on the project at path ":datavein-oracle-native"
     */
    public DataveinOracleNativeProjectDependency getDataveinOracleNative() { return new DataveinOracleNativeProjectDependency(getFactory(), create(":datavein-oracle-native")); }

    /**
     * Creates a project dependency on the project at path ":feature-module"
     */
    public FeatureModuleProjectDependency getFeatureModule() { return new FeatureModuleProjectDependency(getFactory(), create(":feature-module")); }

    /**
     * Creates a project dependency on the project at path ":oracle-drive-integration"
     */
    public OracleDriveIntegrationProjectDependency getOracleDriveIntegration() { return new OracleDriveIntegrationProjectDependency(getFactory(), create(":oracle-drive-integration")); }

    /**
     * Creates a project dependency on the project at path ":romtools"
     */
    public RomtoolsProjectDependency getRomtools() { return new RomtoolsProjectDependency(getFactory(), create(":romtools")); }

    /**
     * Creates a project dependency on the project at path ":sandbox-ui"
     */
    public SandboxUiProjectDependency getSandboxUi() { return new SandboxUiProjectDependency(getFactory(), create(":sandbox-ui")); }

    /**
     * Creates a project dependency on the project at path ":secure-comm"
     */
    public SecureCommProjectDependency getSecureComm() { return new SecureCommProjectDependency(getFactory(), create(":secure-comm")); }

}
