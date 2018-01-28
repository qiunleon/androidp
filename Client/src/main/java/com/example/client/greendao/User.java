package com.example.client.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.client.dao.DaoSession;
import com.example.client.dao.SheepDao;
import com.example.client.dao.CatDao;
import com.example.client.dao.UserDao;

@Entity(
        // schema 名,多个 schema 时设置关联实体.插件产生不支持,需使用产生器.
        // schema = "myschema",

        // 标记一个实体是否处于活动状态,活动实体有 update、delete、refresh 方法.默认为 false.
        active = false,

        // 表名，默认为类名
        nameInDb = "AWESOME_USERS",

        // 定义多列索引
        indexes = {
                @Index(value = "name DESC", unique = true)
        },

        // 标记是否创建表,默认 true.多实体对应一个表或者表已创建,不需要 greenDAO 创建时设置 false.
        createInDb = true,

        // 是否产生所有参数构造器,默认为 true.无参构造器必定产生.
        generateConstructors = true,

        // 如果没有 get/set 方法,是否生成.默认为 true.
        generateGettersSetters = true
)
public class User {

    /**
     * @Generated greenDao生产代码注解, 手动修改报错
     * @Keep 替换@Generated, greenDao不再生成, 也不报错.@Generated(无hash)也有相同的效果
     * @ToOne joinProperty 和对象联动, 同时改变. 对象懒加载，第一次请求后缓存
     * @ToMany 集合懒加载并缓存, 之后获取集合不查找数据库, 即集合数据不变. 须手动修改集合, 或调用reset方法清理集合
     */

    // 数据库主键，autoincrement设置自增，只能为 long/ Long 类型
    @Id(autoincrement = true)
    private Long id;

    // 唯一，默认索引。可另定义属性唯一索引设为主键
    @Unique
    private String userId;

    // 列名，默认使用变量名。默认变化：userName --> USER_NAME
    @Property(nameInDb = "USERNAME")
    private String name;

    // 索引，unique设置唯一，name设置索引别名
    @Index(unique = true)
    private long fk_dogId;

    // 非空
    @NotNull
    private String alias;

    // 忽略，不持久化，可用关键字transient替代
    @Transient
    private int tempUsageCount;

    // 对多。实体ID对应外联实体属性 referencedJoinProperty
    @ToMany(referencedJoinProperty = "fk_userId")
    private List<Cat> cats;

    // 对多。@JoinProperty：name 实体属性对应外联实体属性 referencedName
    @ToMany(joinProperties = {
            @JoinProperty(name = "alias", referencedName = "dogAlias")
    })
    private List<Dog> dogs;

    // 对多。@JoinEntity：entity 中间表；中间表属性 sourceProperty 对应实体ID；中间表属性 targetProperty 对应外联实体ID
    @ToMany
    @JoinEntity(
            entity = JoinUserWithSheep.class,
            sourceProperty = "uId",
            targetProperty = "sId"
    )

    private List<Sheep> sheep;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1507654846)
private transient UserDao myDao;

@Generated(hash = 1738034108)
public User(Long id, String userId, String name, @NotNull String alias) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.alias = alias;
}

@Generated(hash = 586692638)
public User() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getUserId() {
    return this.userId;
}

public void setUserId(String userId) {
    this.userId = userId;
}

public String getName() {
    return this.name;
}

public void setName(String name) {
    this.name = name;
}



/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 860374627)
public List<Cat> getCats() {
    if (cats == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        CatDao targetDao = daoSession.getCatDao();
        List<Cat> catsNew = targetDao._queryUser_Cats(id);
        synchronized (this) {
            if (cats == null) {
                cats = catsNew;
            }
        }
    }
    return cats;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1845368239)
public synchronized void resetCats() {
    cats = null;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 900285892)
public List<Sheep> getSheep() {
    if (sheep == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        SheepDao targetDao = daoSession.getSheepDao();
        List<Sheep> sheepNew = targetDao._queryUser_Sheep(id);
        synchronized (this) {
            if (sheep == null) {
                sheep = sheepNew;
            }
        }
    }
    return sheep;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 952268405)
public synchronized void resetSheep() {
    sheep = null;
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 2059241980)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getUserDao() : null;
}
}