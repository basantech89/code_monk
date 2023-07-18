const path = require('path');
const CopyPlugin = require('copy-webpack-plugin');
const HtmlPlugin = require('html-webpack-plugin')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')

function getHtmlPlugins(chunks) {
  return chunks.map(chunk => new HtmlPlugin({
    title: 'React Extension',
    filename: `${chunk}.html`,
    chunks: [chunk] // array of chunks that we want to inject into the chunk.html file. 
      // chunk represents a single JS file that is produced by webpack
  }))
}

module.exports = {
  entry: {
    popup: path.resolve('src/popup/popup.tsx'), // popup chunk
    options: path.resolve('src/options/options.tsx'), // options chunk
    background: path.resolve('src/background/background.ts'), // background chunk
    contentScript: path.resolve('src/contentScript/contentScript.ts'), // contentScript chunk
  },
  module: {
    rules: [
      {
        use: 'ts-loader',
        test: /\.tsx?$/,
        exclude: /node_modules/
      },
      {
        use: ['style-loader', 'css-loader'],
        test: /\.css$/i
      },
      {
        type: 'asset/resource',
        test: /\.(jpg|jpeg|png|woff|woff2|eot|ttf|svg)$/
      }
    ]
  },
  plugins: [
    new CleanWebpackPlugin({
      cleanStaleWebpackAssets: false 
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/static'),
          to: path.resolve('dist')
        }
      ]
    }),
    ...getHtmlPlugins(['popup', 'options'])
  ],
  resolve: {
    extensions: ['.tsx', '.ts', '.js']
  },
  output: {
    filename: '[name].js', // we should rename index.js to this, otherwise every chunk that webpack produces will be named index.js 
      // [name] resolves to the chunk name that is being processed by the webpack. 
      // Each chunk has it's own dependency graph and different dependencies
    path: path.resolve(__dirname, 'dist')
  },
  optimization: {
    splitChunks: {
      chunks: 'all'
    }
  }
}