import { Route, Routes } from 'react-router-dom';
import './App.css';
import Navbar from './components/navbar/navbar';
import Base from './pages/base/base';
import Home from './pages/home/home';

function App() {
	return (
		<div className='root'>
			<Navbar/>
			<Routes>
				<Route path='/' 
					element={
						<Base/>
					}>
				</Route>
				<Route path='/home' 
					element={
						<Home/>
					}>
				</Route>
			</Routes>
		</div>
	);
}

export default App;
