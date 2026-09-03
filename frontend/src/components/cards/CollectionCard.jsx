import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSun, faShower, faSeedling } from "@fortawesome/free-solid-svg-icons";
import { altFromFileName } from "../../utils/altFromFileName";
import { useNavigate } from "react-router";
import Button from "../ui/Button";

export default function CollectionCard({ collectionId, imgPath, name, light, water, fertilize }) {
    const navigate = useNavigate();

    return (
        <div className="collection-card">
            <img
                src={`${import.meta.env.BASE_URL}images/plants/${imgPath}`}
                alt={altFromFileName(imgPath)}
                className="collection-card-picture"
            />
            <div className="collection-card-right-container">
                <h2 className="collection-card-title">{name}</h2>
                <div className="care-info-container">
                    <h3 className="care-info-title">Care Instructions:</h3>
                    <div className="care-info-div">
                        <FontAwesomeIcon icon={faSun} className="sun-icon" />
                        <strong>Light:</strong> {light}
                    </div>
                    <div className="care-info-div">
                        <FontAwesomeIcon icon={faShower} className="water-icon" />
                        <strong>Water:</strong> {water}
                    </div>
                    <div className="care-info-div">
                        <FontAwesomeIcon icon={faSeedling} className="fertilize-icon" />
                        <strong>Fertilize:</strong> {fertilize}
                    </div>
                </div>
                <Button
                    innerText="See Plant Details"
                    onClick={() => navigate(`/currentCollection/${collectionId}`)}
                />
            </div>
        </div>
    );
}
