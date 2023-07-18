import React from 'react'
import {
	Box,
	Button,
	Card,
	CardActions,
	CardContent,
	Grid,
	Typography
} from '@material-ui/core'

import {
	OpenWeatherData,
	OpenWeatherTempScale,
	fetchOpenWeatherData,
	getWeatherIconSrc
} from '../../utils/api'

import './index.css'

const WeatherCardContainer: React.FC<{
	children: React.ReactNode
	onDelete?: () => void
}> = ({ children, onDelete }) => {
	return (
		<Box mx='4px' my='16px'>
			<Card>
				<CardContent>{children}</CardContent>
				<CardActions>
					{onDelete && (
						<Button color='secondary' onClick={onDelete}>
							<Typography className='weatherCard-body'>Delete</Typography>
						</Button>
					)}
				</CardActions>
			</Card>
		</Box>
	)
}

type WeatherCardState = 'loading' | 'error' | 'ready'

const WeatherCard: React.FC<{
	city: string
	onDelete?: () => void
	tempScale: OpenWeatherTempScale
}> = ({ city, onDelete, tempScale }) => {
	const [weatherData, setWeatherData] = React.useState<OpenWeatherData | null>(
		null
	)

	const [cardState, setCardState] = React.useState<WeatherCardState>('loading')

	React.useEffect(() => {
		fetchOpenWeatherData(city, tempScale)
			.then(data => {
				setWeatherData(data)
				setCardState('ready')
			})
			.catch(err => setCardState('error'))
	}, [city, tempScale])

	if (cardState === 'loading' || cardState === 'error') {
		return (
			<WeatherCardContainer onDelete={onDelete}>
				<Typography className='weatherCard-title'>{city}</Typography>
				<Typography className='weatherCard-body'>
					{cardState === 'loading'
						? 'Loading...'
						: 'Error: Could not retrieve weather data for this city'}
				</Typography>
			</WeatherCardContainer>
		)
	}

	return (
		<WeatherCardContainer onDelete={onDelete}>
			<Grid container justifyContent='space-around'>
				<Grid item>
					<Typography className='weatherCard-title'>
						{weatherData.name}
					</Typography>
					<Typography className='weatherCard-temp'>
						{Math.round(weatherData.main.temp)}
					</Typography>
					<Typography className='weatherCard-body'>
						Feels like {Math.round(weatherData.main.feels_like)}
					</Typography>
				</Grid>
				<Grid item>
					{weatherData.weather.length > 0 && (
						<>
							<img src={getWeatherIconSrc(weatherData.weather[0].icon)} />
							<Typography className='weatherCard-body'>
								{weatherData.weather[0].main}
							</Typography>
						</>
					)}
				</Grid>
			</Grid>
		</WeatherCardContainer>
	)
}

export default WeatherCard
