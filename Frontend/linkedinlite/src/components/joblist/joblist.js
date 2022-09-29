import './joblist.css';
import Job from '../../components/job/job';
import { useEffect, useState } from 'react';
import jobApi from '../../services/job';

function JobList() {

	// let jobList = [
	// 	{
	// 		"jobId": 1,
	// 		"jobRole": "SDE1",
	// 		"jobDescription": "Full Stack Developer",
	// 		"requiredSkills": [
	// 			"React Js",
	// 			"Spring Boot"
	// 		],
	// 		"fromExperienceInYears": 2,
	// 		"toExperienceInYears": 3,
	// 		"fromCtcInLakhs": 12,
	// 		"toCtcInLakhs": 23,
	// 		"companyId": 1,
	// 		"createdOn": "2022-09-28"
	// 	},
	// 	{
	// 		"jobId": 2,
	// 		"jobRole": "SDE2",
	// 		"jobDescription": "Full Stack Developer",
	// 		"requiredSkills": [
	// 			"React Js",
	// 			"Spring Boot"
	// 		],
	// 		"fromExperienceInYears": 2,
	// 		"toExperienceInYears": 3,
	// 		"fromCtcInLakhs": 12,
	// 		"toCtcInLakhs": 23,
	// 		"companyId": 1,
	// 		"createdOn": "2022-09-28"
	// 	},
	// 	{
	// 		"jobId": 3,
	// 		"jobRole": "SDE3",
	// 		"jobDescription": "Full Stack Developer",
	// 		"requiredSkills": [
	// 			"React Js",
	// 			"Spring Boot"
	// 		],
	// 		"fromExperienceInYears": 2,
	// 		"toExperienceInYears": 3,
	// 		"fromCtcInLakhs": 12,
	// 		"toCtcInLakhs": 23,
	// 		"companyId": 2,
	// 		"createdOn": "2022-09-28"
	// 	},
	// 	{
	// 		"jobId": 4,
	// 		"jobRole": "SDE4",
	// 		"jobDescription": "Full Stack Developer",
	// 		"requiredSkills": [
	// 			"React Js",
	// 			"Spring Boot"
	// 		],
	// 		"fromExperienceInYears": 2,
	// 		"toExperienceInYears": 3,
	// 		"fromCtcInLakhs": 12,
	// 		"toCtcInLakhs": 23,
	// 		"companyId": 2,
	// 		"createdOn": "2022-09-28"
	// 	}
	// ];
	
	const [jobList, setJobList] = useState([]);

	useEffect(() => {
		jobApi.getJobs().then(response => {
			setJobList(response.data.payload);
			console.log("joblist: " + response.data.message);
		});
	}, []);

	return (
		<div className='joblist-root'>
			<div className='joblist-container'>
				{
					jobList.map(job => {
						return <Job key={job.jobId} {...job} applyBtn={true}/>
					})
				}
			</div>
			<nav aria-label="Page navigation job-pagination-border">
				<ul className="pagination justify-content-center">
					<li className="page-item">
						<a className="page-link" href='http://localhost:3000/home' tabIndex="-1">Previous</a>
					</li>
					<li className="page-item"><a className="page-link" href='http://localhost:3000/home'>1</a></li>
					<li className="page-item"><a className="page-link" href='http://localhost:3000/home'>2</a></li>
					<li className="page-item"><a className="page-link" href='http://localhost:3000/home'>3</a></li>
					<li className="page-item">
						<a className="page-link" href='http://localhost:3000/home'>Next</a>
					</li>
				</ul>
			</nav>
		</div>
	);
}

export default JobList;
