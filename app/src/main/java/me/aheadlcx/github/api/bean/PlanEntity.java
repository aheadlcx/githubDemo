package me.aheadlcx.github.api.bean;

/**
 * Description:
 * author: aheadlcx
 * Date:2022/11/15 6:24 下午
 */
public class PlanEntity {
    private String name;
    private int space;
    private int private_repos;
    private int collaborators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getPrivate_repos() {
        return private_repos;
    }

    public void setPrivate_repos(int private_repos) {
        this.private_repos = private_repos;
    }

    public int getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(int collaborators) {
        this.collaborators = collaborators;
    }
}
