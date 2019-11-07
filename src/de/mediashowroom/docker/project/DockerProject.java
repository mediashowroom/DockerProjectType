/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mediashowroom.docker.project;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.CommonProjectActions;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author rene
 */
public class DockerProject implements Project {

    private final FileObject projectDir;
    private final ProjectState state;
    private Lookup lkp;

    public DockerProject(FileObject projectDir, ProjectState state) {
        this.projectDir = projectDir;
        this.state = state;
    }
    
    @Override
    public FileObject getProjectDirectory() {
        return projectDir;
    }

    @Override
    public Lookup getLookup() {
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                new Info(),
                new DockerProjectLogicalView(this)
            });
        }
            
        return lkp;
    }

    private final class ProjectNode extends FilterNode {

        final DockerProject project;
        
        @StaticResource()
        public static final String DOCKER_ICON = "de/mediashowroom/docker/project/docker-16.png";
        
        public ProjectNode(Node node, DockerProject project) throws DataObjectNotFoundException {
            super(node, new FilterNode.Children(node),
                    new ProxyLookup(new Lookup[]{
                        Lookups.singleton(project),
                        node.getLookup()
                    }));
            
            this.project = project;
        }

        @Override
        public Action[] getActions(boolean context) {
            return new Action[]{
                CommonProjectActions.newFileAction(),
                CommonProjectActions.copyProjectAction(),
                CommonProjectActions.deleteProjectAction(),
                CommonProjectActions.closeProjectAction()
            };
        }

        @Override
        public Image getIcon(int type) {
            return ImageUtilities.loadImage(DOCKER_ICON);
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDisplayName() {
            return project.getProjectDirectory().getName();
        }

        
    }
    
    private final class Info implements ProjectInformation {

        @StaticResource()
        public static final String DOCKER_ICON = "de/mediashowroom/docker/project/docker-16.png";
        
        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(DOCKER_ICON));
        }

        @Override
        public Project getProject() {
            return DockerProject.this;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener arg0) {
            
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener arg0) {
            
        }
        
    }
    
    class DockerProjectLogicalView implements LogicalViewProvider {

        @StaticResource()
        public static final String DOCKER_ICON = "de/mediashowroom/docker/project/docker-16.png";
        
        private final DockerProject project;

        public DockerProjectLogicalView(DockerProject project) {
            this.project = project;
        }
        
        
        @Override
        public Node createLogicalView() {
            try {
                FileObject projectDirectory = project.getProjectDirectory();
                DataFolder projectFolder = DataFolder.findFolder(projectDirectory);
                Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
                
                return new ProjectNode(nodeOfProjectFolder, project);
            } catch (DataObjectNotFoundException ex) {
                return new AbstractNode(Children.LEAF);
            }
        }

        @Override
        public Node findPath(Node arg0, Object arg1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
