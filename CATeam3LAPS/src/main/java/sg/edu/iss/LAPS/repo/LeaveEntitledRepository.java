package sg.edu.iss.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.iss.LAPS.model.LeaveEntitled;
import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.utility.LeaveStatus;

import java.util.ArrayList;

public interface LeaveEntitledRepository extends JpaRepository<LeaveEntitled,Integer> {
    @Query("SELECT l FROM LeaveEntitled l where l.user.id = :id")
    public ArrayList<LeaveEntitled> findLeaveEntitledByUser(@Param("id") Long id);
    
    @Query("SELECT l FROM LeaveEntitled l where l.user.id = :id AND l.leaveType.leaveTypeId = 3")
    public LeaveEntitled findCompensationLeaveByUserId(@Param("id") Long id);

    @Query("SELECT l FROM LeaveEntitled l where l.user.id = :userId AND l.leaveType.leaveTypeId = :leaveId")
    public LeaveEntitled findLeaveEntitledByUserIdAndLeaveId(@Param("userId") Long userId, @Param("leaveId") Integer leaveId);
    @Query("SELECT l FROM LeaveEntitled l where l.user.id = :id AND l.leaveType.leaveTypeId = :ltid")
    public LeaveEntitled findLeaveEntitledByUserId(@Param("id") Long id, @Param("ltid") Integer ltid);

    @Query("SELECT l.totalLeave FROM LeaveEntitled l where l.user.id = :userId AND l.leaveType.leaveTypeId = :leaveId ")
    public Float totalAvailableLeave(@Param("userId") Long userId, @Param("leaveId") Integer leaveId);
}
