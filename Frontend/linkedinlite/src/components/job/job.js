import './job.css';
import userApi from '../../services/user';

const Job = (props) => {

	function applyJob(event) {
        event.preventDefault();
		
		let userId = localStorage.getItem("userId");
		let jobId = props.jobId;
		userApi.applyJob(userId, jobId).then(response => {
			alert("job: " + response.data.message);
		});
	}

	return (
		<div className="job-root">
			<div className="card col-12">
				<form onSubmit={applyJob}>
					<div className="card-body">
						<div className="card-subbody">
							<div className="job-role">
								<p>{ props.jobRole }</p>
							</div>
							<div className="card-enditem job-ctc">
								<p><strong>CTC : </strong>{ props.fromCtcInLakhs } - { props.toCtcInLakhs } in Lakhs</p>
							</div>
						</div>
						<div className="card-subbody">
							<div className="job-company-name">
								<p><strong>Company Name : </strong>{ props.companyId }</p>
							</div>
							<div className="card-enditem job-exp">
								<p><strong>Experience : </strong>{ props.fromExperienceInYears } - { props.toExperienceInYears } years experience</p>
							</div>
						</div>
						<div className="card-subbody">
							<p className="card-text">{props.jobDescription}</p>
						</div>
						<div className="card-subbody">
							<div className="job-req-skills">
								<div className="job-skills">
									{
										props.requiredSkills ? 
										props.requiredSkills.map((skill, i) => {
											return <p className="btn btn-success job-skill" key={i}>{skill}</p>
										}) : <p>None</p>
									}
								</div>
							</div>
							{
								(props.applyBtn) ?
									<div className="card-enditem">
										<button type="submit" className="btn btn-primary job-apply-btn">Apply Job</button>
									</div> :
									<div></div>
							}
						</div>
					</div>
				</form>
			</div>
		</div>
	);
}

export default Job;
