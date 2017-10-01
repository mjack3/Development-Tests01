
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	//Carpetas de mensajes por actor
	@Query("select a.folders from Actor a where a.id=?1 ")
	List<Folder> foldersByActor(int actor_id);

	@Query("select c.folders from Actor c where c.userAccount.username = ?1")
	List<Folder> folderOfSelf(String username);

	@Query("select c from Actor c where c.userAccount.username = ?1")
	Actor selectByUsername(String username);

}
