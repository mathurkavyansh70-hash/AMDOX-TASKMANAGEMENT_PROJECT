package com.TaskManagement.TaskManagement3.Security;
import com.TaskManagement.TaskManagement3.Enum.*;
import java.util.*;
public class RoleBasedPermission {
    static Map<Role, Set<Permissions>>getRolePermission(){
    Map<Role, Set<Permissions>> map = new HashMap<>();
    map.put(Role.ADMIN, new HashSet<>(Arrays.asList(Permissions.values())));
    map.put(Role.MANAGER, new HashSet<>(Arrays.asList(Permissions.ISSUE_VIEW, 
                                                    Permissions.COMMENT_ADD,
                                                    Permissions.ISSUE_CREATE,
                                                    Permissions.ISSUE_EDIT)));

    map.put(Role.DEVELOPER, new HashSet<>(Arrays.asList(Permissions.ISSUE_VIEW, 
                                                    Permissions.COMMENT_ADD,
                                                    Permissions.ISSUE_EDIT)));

    map.put(Role.TESTER, new HashSet<>(Arrays.asList(Permissions.ISSUE_VIEW, 
                                                    Permissions.COMMENT_ADD)));
    
    return map;
    }

}
