/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mediashowroom.docker.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author rene
 */
@ServiceProvider(service=ProjectFactory.class)
public class DockerProjectFactory  implements ProjectFactory {

    public static final String PROJECT_FILE = "Dockerfile";
    public static final String PROJECT_FILE_2 = "docker-compose.yml";
    
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return (projectDirectory.getFileObject(PROJECT_FILE) != null) 
                || (projectDirectory.getFileObject(PROJECT_FILE_2) != null);
    }

    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new DockerProject(dir, state) : null;
    }

    @Override
    public void saveProject(Project arg0) throws IOException, ClassCastException {
    }
    
}
