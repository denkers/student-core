
package engine.views.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import engine.controllers.ControllerMessage;
import engine.core.ExceptionOutput;
import engine.models.AdminAnnouncementsModel;
import engine.views.GUIView;
import engine.views.gui.layout.Layout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;


public class AdminControlPanelView extends GUIView implements ActionListener
{
    private BufferedImage backgroundImage;
    private BufferedImage classesMenuImage;
    private BufferedImage usersMenuImage;
    private BufferedImage deptMenuImage;
    private BufferedImage announceMenuImage;
    
    private JPanel adminPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel adminControls;
    private JPanel adminPaneView;
    
    private final String CLASS_VIEW     =   "classes_v";
    private final String USERS_VIEW     =   "users_v";
    private final String DEPT_VIEW      =   "dept_v";
    private final String ANNOUNCE_VIEW  =   "annouce_v";  
    
    private AdminAnnouncementView announcementsView;
   /* private JList announcementList;
    private UpdateListModel announcementModel;
    private JButton addAnnouncementButton;
    private JButton removeAnnouncementButton;
    private JButton editAnnouncementButton; */
    
    private JPanel classesView;
    private JTable usersTable;
    
    private JPanel usersView;
    private JPanel departmentView;
    
    private JButton showUsersButton;
    private JButton showClassesButton;
    private JButton showDepartmentButton;
    private JButton showAnnouncementsButton;
    
    public AdminControlPanelView()
    {
        super();
    }
    
    public AdminControlPanelView(ControllerMessage data)
    {
        super(data);
    }

    @Override
    protected void initComponents() 
    {
        panel   =   new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        
        adminPanel      =   new JPanel(new BorderLayout());
        leftPanel       =   new JPanel();
        rightPanel      =   new JPanel();
        adminControls   =   new JPanel(new GridLayout(4, 1));
        adminPaneView   =   new JPanel(new CardLayout());
        
        adminPanel.setPreferredSize(new Dimension(600, 400));
        leftPanel.setPreferredSize(new Dimension(150, adminPanel.getPreferredSize().height));
        adminControls.setPreferredSize(new Dimension(leftPanel.getPreferredSize().width, 250));
        
        adminPanel.setBackground(Color.WHITE);
        leftPanel.setBackground(new Color(50, 50, 62));
        rightPanel.setBackground(Color.WHITE);
        adminControls.setBackground(new Color(50, 50, 62));
        panel.setBackground(Color.WHITE);
        
        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY));
        

        initClassesView();
        initDepartmentsView();
        initUsersView();
    //    initAnnouncementsView();
        
        announcementsView   =   new AdminAnnouncementView();
        adminPaneView.add(classesView, CLASS_VIEW);
        adminPaneView.add(departmentView, DEPT_VIEW);
        adminPaneView.add(usersView, USERS_VIEW);
        adminPaneView.add(announcementsView.announcementPanel, ANNOUNCE_VIEW);
        
        
        showClassesButton       =   new JButton("Classes");
        showDepartmentButton    =   new JButton("Departments");
        showUsersButton         =   new JButton("Users");
        showAnnouncementsButton =   new JButton("Announcements");
        
        showClassesButton.setIcon(new ImageIcon(classesMenuImage));
        showDepartmentButton.setIcon(new ImageIcon(deptMenuImage));
        showUsersButton.setIcon(new ImageIcon(usersMenuImage));
        showAnnouncementsButton.setIcon(new ImageIcon(announceMenuImage));
        
        showClassesButton.setForeground(Color.WHITE);
        showDepartmentButton.setForeground(Color.WHITE);
        showUsersButton.setForeground(Color.WHITE);
        showAnnouncementsButton.setForeground(Color.WHITE);
        
        
        adminControls.add(showAnnouncementsButton);
        adminControls.add(showClassesButton);
        adminControls.add(showDepartmentButton);
        adminControls.add(showUsersButton);
        
        leftPanel.add(adminControls);
        JScrollPane adminPaneScroller   =   new JScrollPane(adminPaneView);
        adminPaneScroller.setBorder(null);
        rightPanel.add(adminPaneScroller);
        
        adminPanel.add(leftPanel, BorderLayout.WEST);
        adminPanel.add(rightPanel, BorderLayout.CENTER);
        
        panel.add(Box.createRigidArea(new Dimension(panel.getPreferredSize().width, 500)));
        panel.add(adminPanel);
        
        showAdminView(ANNOUNCE_VIEW);
    }

    private void initDepartmentsView()
    {
        departmentView  =   new JPanel();
        departmentView.setBackground(Color.WHITE);
    }
    
    private void initClassesView()
    {
        classesView         =   new JPanel();
        classesView.setBackground(Color.WHITE);
    }
    
    private void initUsersView()
    {
        usersView   =   new JPanel();
        usersView.setBackground(Color.WHITE);
        
        usersTable  =   new JTable(new DefaultTableModel());
        usersView.add(new JScrollPane(usersTable));
    }
    
  /*  private void initAnnouncementsView()
    {
        announcementsView           =   new JPanel(new BorderLayout());
        JPanel announcementHeader   =   new JPanel();   
        JPanel announcementControls =   new JPanel(new GridLayout(1, 3));
        addAnnouncementButton       =   new JButton("Add");
        removeAnnouncementButton    =   new JButton("Remove");
        editAnnouncementButton      =   new JButton("Edit");
        
        addAnnouncementButton.setIcon(new ImageIcon(addSmallImage));
        removeAnnouncementButton.setIcon(new ImageIcon(removeSmallImage));
        editAnnouncementButton.setIcon(new ImageIcon(editSmallImage));
        
        JPanel announcementsWrapper =   new JPanel();
        announcementsView.setBackground(Color.WHITE);
        announcementHeader.setBackground(Color.WHITE);
        announcementsWrapper.setBackground(Color.WHITE);
        announcementControls.setBackground(Color.WHITE);
        
        announcementControls.add(addAnnouncementButton);
        announcementControls.add(removeAnnouncementButton);
        announcementControls.add(editAnnouncementButton);

        announcementModel   =   new UpdateListModel();
        announcementList    =   new JList(announcementModel);
        announcementList.setCellRenderer(new AnnouncementCellRenderer());
        announcementList.setPreferredSize(new Dimension(380, 150));
        announcementsWrapper.setPreferredSize(new Dimension(400, 150));
        announcementControls.setBorder(BorderFactory.createTitledBorder("Controls"));
        
        
        JsonArray announcements =   AdminAnnouncementsModel.getAllAnnouncements();
        
        if(announcements.size() > 0) 
        {
            announcementsView.add(announcementList);
            for(int i = 1; i < announcements.size(); i++)
                announcementModel.addElement(announcements.get(i).getAsJsonObject());
            
            announcementList.setSelectedIndex(0);
        }
        
        JScrollPane announcementScroller    =   new JScrollPane(announcementList);
        announcementScroller.setPreferredSize(new Dimension(390, 300));
        announcementsWrapper.add(announcementScroller);
        
        
        
        announcementHeader.add(announcementControls, BorderLayout.EAST);
        announcementHeader.setPreferredSize(new Dimension(1, 75));
        announcementHeader.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        
        announcementsView.add(announcementHeader, BorderLayout.NORTH);
        announcementsView.add(announcementsWrapper, BorderLayout.CENTER);
        
    } */
    
    @Override
    protected void initResources() 
    {
        try
        {
            backgroundImage     =   ImageIO.read(new File(Layout.getImage("background2.jpg")));
            announceMenuImage   =   ImageIO.read(new File(Layout.getImage("notes_icon.png")));
            classesMenuImage    =   ImageIO.read(new File(Layout.getImage("books_icon.png")));
            deptMenuImage       =   ImageIO.read(new File(Layout.getImage("department_large_icon.png")));
            usersMenuImage      =   ImageIO.read(new File(Layout.getImage("users_icon.png")));
        }
        
        catch(IOException e)
        {
            ExceptionOutput.output("Failed to load resources: " + e.getMessage(), ExceptionOutput.OutputType.MESSAGE);
        }
    }

    @Override
    protected void initListeners()
    {
        showClassesButton.addActionListener(this);
        showUsersButton.addActionListener(this);
        showDepartmentButton.addActionListener(this);
        showAnnouncementsButton.addActionListener(this);
       /* addAnnouncementButton.addActionListener(this);
        removeAnnouncementButton.addActionListener(this);
        editAnnouncementButton.addActionListener(this); */
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object src  =   e.getSource();
        
        if(src == showClassesButton)
            showAdminView(CLASS_VIEW);
        
        else if(src == showUsersButton)
            showAdminView(USERS_VIEW);
        
        else if(src == showDepartmentButton)
            showAdminView(DEPT_VIEW);
        
        else if(src == showAnnouncementsButton)
            showAdminView(ANNOUNCE_VIEW);
        
        /*else if(src == addAnnouncementButton)
            addAnnouncement();
        
        else if(src == editAnnouncementButton)
            editAnnouncement();
        
        else if(src == removeAnnouncementButton)
            removeAnnouncement(); */
        
    }
    
    /*private void addAnnouncement()
    {
        
    }
    
    private void editAnnouncement()
    {
        
    }
    
    private void removeAnnouncement()
    {
        
    } */
    
    private void showAdminView(String viewName)
    {
        CardLayout cLayout  =   (CardLayout) adminPaneView.getLayout();
        cLayout.show(adminPaneView, viewName);
    }
    
  /*  private class AnnouncementCellRenderer implements ListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
        {
            JsonObject jObj             =   (JsonObject) value;
            JPanel announcementPanel    =   new JPanel(new BorderLayout());
            JPanel announceHeaderPanel  =   new JPanel();
            JPanel announceInfoPanel    =   new JPanel();
            JPanel contentWrapper       =   new JPanel();
            JLabel announcerLabel       =   new JLabel(jObj.get("ANNOUNCER").getAsString());
            JLabel announceDateLabel    =   new JLabel(jObj.get("ANNOUNCE_DATE").getAsString());
            JTextArea content           =   new JTextArea(jObj.get("CONTENT").getAsString());
            
            announcementPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            announcementPanel.setBackground(Color.WHITE);
            announceHeaderPanel.setBackground(Color.BLACK);
            content.setBackground(Color.WHITE);
            contentWrapper.setBackground(Color.WHITE);
            announcerLabel.setForeground(Color.WHITE);
            announceDateLabel.setForeground(Color.WHITE);
            announceInfoPanel.setBackground(Color.BLACK);
            
            announceInfoPanel.add(announcerLabel);
            announceInfoPanel.add(announceDateLabel);
            announceHeaderPanel.add(announceInfoPanel, BorderLayout.WEST);
            
            JScrollPane contentScrollPane   =   new JScrollPane(content);
            contentScrollPane.setBorder(null);
            contentWrapper.add(contentScrollPane);
            
            announcementPanel.add(announceHeaderPanel, BorderLayout.NORTH);
            announcementPanel.add(contentWrapper, BorderLayout.CENTER);
            if(index == announcementList.getSelectedIndex()) 
            {
                contentWrapper.setVisible(true);
                announcementModel.update(index);
                announceHeaderPanel.setBackground(new Color(67, 133, 224));
                announceInfoPanel.setBackground(new Color(67, 133, 224));
            }
            else 
            {
                contentWrapper.setVisible(false);
                announcementModel.update(index);

            }
           
            return announcementPanel;
        }
    }*/
    
    private class AdminAnnouncementView extends AnnouncementView
    {

        @Override
        protected void addAnnouncement() {
        }

        @Override
        protected void removeAnnouncement() {
        }

        @Override
        protected void editAnnouncement() {
        }

        @Override
        protected JsonArray getData()
        {
            return AdminAnnouncementsModel.getAllAnnouncements();
        }
        
    }
    
    private class UpdateListModel extends DefaultListModel
    {
        public void update(int index)
        {
            fireContentsChanged(this, index, index);
        }
    }
    
    
}