const OPEN_WEATHER_API_KEY = '5e840ff486f37a3f852a977b1ea5b2a4'

export interface OpenWeatherData {
	name: string
	main: {
		temp: number
		feels_like: number
		humidity: number
		pressure: number
		temp_max: number
		temp_min: number
	}
	weather: {
		description: string
		icon: string
		id: number
		main: string
	}[]
	wind: {
		deg: number
		speed: number
	}
}

export type OpenWeatherTempScale = 'metric' | 'imperial'

export async function fetchOpenWeatherData(
	city: string,
	tempScale: OpenWeatherTempScale
): Promise<OpenWeatherData> {
	const res = await fetch(
		`https://api.openweathermap.org/data/2.5/weather?q=${city}&units=${tempScale}&appid=${OPEN_WEATHER_API_KEY}`
	)

	if (!res.ok) {
		throw new Error('City not found')
	}

	const data = await res.json()
	return data
}

export function getWeatherIconSrc(iconCode: string) {
	return `https://openweathermap.org/img/wn/${iconCode}@2x.png`
}
