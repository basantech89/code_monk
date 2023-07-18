import React from 'react'
import ReactDOM from 'react-dom'
import { Box, Grid, IconButton, InputBase, Paper } from '@material-ui/core'
import {
	Add as AddIcon,
	PictureInPicture as PictureInPictureIcon
} from '@material-ui/icons'

import WeatherCard from '../components/WatherCard'

import 'fontsource-roboto'
import './popup.css'
import {
	LocalStorageOptions,
	getStoredOptions,
	getStoredCities,
	setStoredCities,
	setStoredOptions
} from '../utils/storage'
import { Messages } from '../utils/messages'

const App: React.FC<{}> = () => {
	const [cities, setCities] = React.useState<string[]>([])
	const [cityInput, setCityInput] = React.useState('')
	const [options, setOptions] = React.useState<LocalStorageOptions | null>(null)

	React.useEffect(() => {
		getStoredCities().then(setCities)
		getStoredOptions().then(setOptions)
	}, [])

	const addCity = () => {
		if (cityInput !== '') {
			const updatedCities = [...cities, cityInput]
			setStoredCities(updatedCities).then(() => {
				setCities(updatedCities)
				setCityInput('')
			})
		}
	}

	const deleteCity = (index: number) => {
		cities.splice(index, 1)
		const updatedCities = [...cities]
		setStoredCities(updatedCities).then(() => {
			setCities(updatedCities)
		})
	}

	const toggleTempScale = () => {
		const updatedOptionsObject: LocalStorageOptions = {
			...options,
			tempScale: options.tempScale === 'metric' ? 'imperial' : 'metric'
		}
		setStoredOptions(updatedOptionsObject).then(() => {
			setOptions(updatedOptionsObject)
		})
	}

	if (!options) {
		return null
	}

	const toggleOverlay = () => {
		chrome.tabs.query({ active: true }, tabs => {
			if (tabs.length > 0) {
				chrome.tabs.sendMessage(tabs[0].id, Messages.TOGGLE_OVERLAY)
			}
		})
	}

	return (
		<Box mx='8px' my='16px'>
			<Grid container justifyContent='space-evenly'>
				<Grid item>
					<Paper>
						<Box px='15px' py='5px'>
							<InputBase
								placeholder='Add a city name'
								value={cityInput}
								onChange={event => setCityInput(event.target.value)}
							/>
							<IconButton onClick={addCity}>
								<AddIcon />
							</IconButton>
						</Box>
					</Paper>
				</Grid>
				<Grid item>
					<Paper>
						<Box py='4px'>
							<IconButton onClick={toggleTempScale}>
								{options.tempScale === 'metric' ? '\u2103' : '\u2109'}
							</IconButton>
						</Box>
					</Paper>
				</Grid>
				<Grid item>
					<Paper>
						<Box py='4px'>
							<IconButton onClick={toggleOverlay}>
								<PictureInPictureIcon />
							</IconButton>
						</Box>
					</Paper>
				</Grid>
			</Grid>

			{options.homeCity && (
				<WeatherCard city={options.homeCity} tempScale={options.tempScale} />
			)}

			{cities.map((city, index) => (
				<WeatherCard
					city={city}
					key={index}
					tempScale={options.tempScale}
					onDelete={() => deleteCity(index)}
				/>
			))}
			<Box height='16px' />
		</Box>
	)
}

const root = document.createElement('div')
document.body.appendChild(root)
ReactDOM.render(<App />, root)
