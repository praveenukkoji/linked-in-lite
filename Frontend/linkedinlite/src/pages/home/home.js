import Filter from '../../components/filter/filter';
import JobList from '../../components/joblist/joblist';
import Profile from '../../components/profile/profile';

import './home.css';

function Home() {
	return (
		<div className='home-root'>
			<div className='home-container'>
                <div className='filter'>
                    <Filter/>
                </div>
                <div className='joblist'>
                    <JobList/>
                </div>
                <div className='profile'>
                    <Profile/>
                </div>
            </div>
		</div>
	);
}

export default Home;
