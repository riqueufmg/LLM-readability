public Boolean onParameterizedType(TypeDescription.Generic parameterizedType) {
    Queue<TypeDescription.Generic> candidates = QueueFactory.make(Collections.singleton(typeDescription));
    Set<TypeDescription> previous = new HashSet<TypeDescription>(Collections.singleton(typeDescription.asErasure()));
    do {
        TypeDescription.Generic candidate = candidates.remove();
        if (candidate.asErasure().equals(parameterizedType.asErasure())) {
            if (candidate.getSort().isNonGeneric()) {
                return true;
            } else {
                TypeList.Generic source = candidate.getTypeArguments(), target = parameterizedType.getTypeArguments();
                int size = target.size();
                if (source.size() != size) {
                    return false;
                }
                for (int index = 0; index < size; index++) {
                    if (!source.get(index).accept(new IsAssignableToVisitor(target.get(index), false))) {
                        return false;
                    }
                }
                TypeDescription.Generic ownerType = parameterizedType.getOwnerType();
                return ownerType == null || ownerType.accept(new IsAssignableToVisitor(ownerType));
            }
        } else if (polymorphic) {
            TypeDescription.Generic superClass = candidate.getSuperClass();
            if (superClass != null && previous.add(superClass.asErasure())) {
                candidates.add(superClass);
            }
            for (TypeDescription.Generic anInterface : candidate.getInterfaces()) {
                if (previous.add(anInterface.asErasure())) {
                    candidates.add(anInterface);
                }
            }
        }
    } while (!candidates.isEmpty());
    return false;
}