import React from 'react';
import Card from 'react-bootstrap/Card';
import InfoData from './HealthInfo.json'
import '../../styles/Card.css'

const HealthInfoCard = () => {

    return (
        <div className='healthInfo'>
            {InfoData.map(data => {
                return (
                    <Card key={data.id} >
                        <Card.Body>
                            <a href='#info'> <Card.Title>{data.title}</Card.Title></a>
                            <Card.Text>{data.category}</Card.Text>
                            <Card.Text className='healthInfoDescription'>{data.description}</Card.Text>
                        </Card.Body>
                    </Card>
                )
            })}
        </div>
    )
}

export default HealthInfoCard
