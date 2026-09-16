import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router";
import { altFromFileName } from "../../utils/altFromFileName";
import Button from "../ui/Button";

export default function SearchCard({ imgPath, name, plant, addPlantToCollection }) {
    const navigate = useNavigate();

    return (
        <div className="search-card">
            <img
                src={`${import.meta.env.BASE_URL}images/plants/${imgPath}`}
                alt={altFromFileName(imgPath)}
                className="search-card-picture"
            />
            <div className="search-card-right-container">
                <h2 className="search-card-title">{name}</h2>
                <Button
                    innerText="Add Plant"
                    icon={faPlus}
                    onClick={() => {
                        addPlantToCollection(plant);
                        navigate("/currentCollection");
                    }}
                />
            </div>
        </div>
    );
}
